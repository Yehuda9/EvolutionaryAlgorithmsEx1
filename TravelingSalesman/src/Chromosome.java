import java.util.Arrays;
import java.util.List;

public class Chromosome {
  private static final Data data = Data.getInstance();

  private final int[] genes;

  private Double fitness = null;

  public Chromosome(int n) {
    this(Generation.random.ints(0, n).distinct().limit(n).toArray());
  }

  public Chromosome(int[] genes) {
    if (Arrays.stream(genes).anyMatch(gene -> gene < 0 || gene > genes.length)) {
      throw new IllegalArgumentException("Invalid gene value");
    }
    if (Arrays.stream(genes).distinct().count() != genes.length) {
      throw new IllegalArgumentException("Duplicate gene value");
    }
    this.genes = genes;
  }

  public int[] getGenes() {
    return Arrays.copyOf(genes, genes.length);
  }

  public int size() {
    return genes.length;
  }

  public double fitness() {
    return fitness != null ? fitness : (fitness = getFitness());
  }

  private double getFitness() {
    List<Point> points = Arrays.stream(genes).mapToObj(data::getPoint).toList();
    double distance = 0;
    for (int i = 0; i < points.size(); i++) {
      distance += points.get(i).distanceTo(points.get((i + 1) % points.size()));
    }
    return distance;
  }

  public Chromosome mutate(double mutationRate) {
    int[] newGenes = getGenes();
    if (Generation.random.nextDouble() < mutationRate) {
      int index1 = Generation.random.nextInt(newGenes.length);
      int index2 = Generation.random.nextInt(newGenes.length);
      while (index1 == index2) {
        index1 = Generation.random.nextInt(newGenes.length);
        index2 = Generation.random.nextInt(newGenes.length);
      }

      int temp = newGenes[index1];
      newGenes[index1] = newGenes[index2];
      newGenes[index2] = temp;
    }
    return new Chromosome(newGenes);
  }

  @Override
  public String toString() {
    return Arrays.toString(genes);
  }
}
