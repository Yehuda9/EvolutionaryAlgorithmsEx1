import java.util.Arrays;
import java.util.Random;

public class Chromosome {
  private final Random random;
  private static final Data data = Data.getInstance();

  private final Point[] genes;

  private Double fitness = null;

  public Chromosome(int n, Random random) {
    this(
        random.ints(0, n).distinct().limit(n).mapToObj(data::getPoint).toArray(Point[]::new),
        random);
  }

  public Chromosome(Point[] genes, Random random) {
    if (Arrays.stream(genes).distinct().count() != genes.length) {
      throw new IllegalArgumentException("Duplicate gene value");
    }
    this.genes = genes;
    this.random = random;
  }

  public Point[] getGenes() {
    return Arrays.copyOf(genes, genes.length);
  }

  public int size() {
    return genes.length;
  }

  public double fitness() {
    return fitness != null ? fitness : (fitness = getFitness());
  }

  private double getFitness() {
    double distance = 0;
    for (int i = 0; i < genes.length; i++) {
      distance += genes[i].distanceTo(genes[(i + 1) % genes.length]);
    }
    return distance;
  }

  public Chromosome mutate(double mutationRate) {
    Point[] newGenes = getGenes();
    if (random.nextDouble() < mutationRate) {
      int index1 = random.nextInt(newGenes.length);
      int index2 = random.nextInt(newGenes.length);
      while (index1 == index2) {
        index1 = random.nextInt(newGenes.length);
        index2 = random.nextInt(newGenes.length);
      }

      Point temp = newGenes[index1];
      newGenes[index1] = newGenes[index2];
      newGenes[index2] = temp;
    }
    return new Chromosome(newGenes, this.random);
  }

  @Override
  public String toString() {
    return Arrays.toString(genes);
  }
}
