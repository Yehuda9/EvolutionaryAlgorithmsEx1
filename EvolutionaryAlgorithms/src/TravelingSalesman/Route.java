package TravelingSalesman;

import Common.Chromosome;
import Common.Gene;
import java.util.Arrays;
import java.util.Random;

public class Route extends Chromosome {
  private static final Data data = Data.getInstance();
  private final Random random;
  private Double fitness = null;

  public Route(int n, Random random) {
    this(
        random.ints(0, n).distinct().limit(n).mapToObj(data::getPoint).toArray(Point[]::new),
        random);
  }

  public Route(Gene[] genes, Random random) {
    super(genes);
    if (Arrays.stream(genes).distinct().count() != genes.length) {
      throw new IllegalArgumentException("Duplicate gene value");
    }
    this.random = random;
  }

  @Override
  public double fitness() {
    return fitness != null ? fitness : (fitness = getFitness());
  }

  @Override
  public Chromosome mutate(double mutationRate) {
    Gene[] newGenes = getGenes();
    if (random.nextDouble() < mutationRate) {
      int index1 = random.nextInt(newGenes.length);
      int index2 = random.nextInt(newGenes.length);
      while (index1 == index2) {
        index1 = random.nextInt(newGenes.length);
        index2 = random.nextInt(newGenes.length);
      }

      Gene temp = newGenes[index1];
      newGenes[index1] = newGenes[index2];
      newGenes[index2] = temp;
    }
    return new Route(newGenes, this.random);
  }

  private double getFitness() {
    double distance = 0;
    Gene[] genes = getGenes();
    for (int i = 0; i < genes.length; i++) {
      distance += ((Point) genes[i]).distanceTo(((Point) genes[(i + 1) % genes.length]));
    }
    return distance;
  }

  @Override
  public String toString() {
    return Arrays.toString(
        Arrays.stream(getGenes()).mapToInt(gene -> data.getIndexOf((Point) gene)).toArray());
  }
}
