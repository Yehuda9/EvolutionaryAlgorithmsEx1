package EightQueenPuzzle;

import Common.Chromosome;
import Common.Gene;
import java.util.Random;
import java.util.stream.Stream;

public class Puzzle extends Chromosome {
  private static final int MAX_FITNESS = 28;
  private final Random random;

  private Double fitness = null;

  public Puzzle(int n, Random random) {
    this(
        Stream.generate(() -> new Row(random.nextInt(n))).distinct().limit(n).toArray(Row[]::new),
        random);
  }

  public Puzzle(Gene[] genes, Random random) {
    super(genes);
    this.random = random;
  }

  @Override
  public double fitness() {
    return fitness != null ? fitness : (fitness = getFitness());
  }

  public double getFitness() {
    int fitness = 0;
    for (int i = 0; i < size(); i++) {
      for (int j = i + 1; j < size(); j++) {
        if (getGenes()[i].equals(getGenes()[j])
            || Math.abs(i - j)
                == Math.abs(((Row) getGenes()[i]).index() - ((Row) getGenes()[j]).index())) {
          fitness++;
        }
      }
    }
    return MAX_FITNESS - fitness;
  }

  @Override
  public Chromosome mutate(double mutationRate) {
    Gene[] newGenes = getGenes();
    if (random.nextDouble() < mutationRate) {
      int index = random.nextInt(newGenes.length);
      newGenes[index] = new Row(random.nextInt(newGenes.length));
    }
    return new Puzzle(newGenes, random);
  }

  @Override
  public int size() {
    return getGenes().length;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        sb.append(getGenes()[i].equals(new Row(j)) ? "Q " : "* ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
