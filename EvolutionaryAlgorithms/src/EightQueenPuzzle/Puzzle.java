package EightQueenPuzzle;

import Common.Chromosome;
import Common.Point;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Puzzle extends Chromosome {
  private static final int MAX_FITNESS = 28;
  private final Random random;

  private Double fitness = null;

  public Puzzle(int n, Random random) {
    this(
        Stream.generate(() -> new Point(random.nextInt(n), 0 /*random.nextInt(n)*/))
            .distinct()
            .limit(n)
            .toArray(Point[]::new),
        random);
  }

  public Puzzle(Point[] genes, Random random) {
    super(genes);
    if (genes.length > 8) {
      System.out.println("genes.length > 8");
    }

    if (Arrays.stream(genes).distinct().count() != genes.length) {
      //      throw new IllegalArgumentException("Duplicate gene value");
    }
    this.random = random;
  }

  @Override
  public double fitness() {
    return fitness != null ? fitness : (fitness = getFitness());
  }

  /*private double getFitness() {
    int fitness = 0;
    for (Point point : getGenes()) {
      for (Point other : getGenes()) {
        if (point.equals(other)) {
          continue;
        }
        if (point.getX() == other.getX()
            || point.getY() == other.getY()
            || Math.abs(point.getX() - other.getX()) == Math.abs(point.getY() - other.getY())) {
          fitness++;
        }
      }
    }
    int r = MAX_FITNESS - (fitness >> 1);
    if (r < 0) {
      System.out.println("Fitness is negative: " + r);
    }
    return r;
  }*/

  public double getFitness() {
    int fitness = 0;
    for (int i = 0; i < size(); i++) {
      for (int j = i + 1; j < size(); j++) {
        if (getGenes()[i] == getGenes()[j]
            || Math.abs(i - j) == Math.abs(getGenes()[i].getX() - getGenes()[j].getX())) {
          fitness++;
        }
      }
    }
    return MAX_FITNESS - fitness;
  }

  /*@Override
  public Chromosome mutate(double mutationRate) {
    Point[] newGenes = getGenes();
    if (random.nextDouble() < mutationRate) {
      int index = random.nextInt(newGenes.length);

      do {
        newGenes[index] =
            new Point(random.nextInt(newGenes.length), random.nextInt(newGenes.length));
      } while (Arrays.stream(newGenes).distinct().count() != newGenes.length);
    }
    return new Puzzle(newGenes, random);
  }*/

  @Override
  public Chromosome mutate(double mutationRate) {
    Point[] newGenes = getGenes();
    if (random.nextDouble() < mutationRate) {
      int index = random.nextInt(newGenes.length);
      newGenes[index] = new Point(random.nextInt(newGenes.length), newGenes[index].getY());
    }
    return new Puzzle(newGenes, random);
  }

  @Override
  public int size() {
    return getGenes().length;
  }

  /*@Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        boolean found = false;
        for (Point point : getGenes()) {
          if (point.equals(new Point(i, j))) {
            sb.append("Q ");
            found = true;
            break;
          }
        }
        if (!found) {
          sb.append("* ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }*/

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        sb.append(getGenes()[i].getX() == j ? "Q " : "* ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
