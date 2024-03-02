import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chromosome {
  public final int n;
  private static final Data data = Data.getInstance();

  private final List<Point> points;
  private final int[] genes;

  private Double fitness = null;

  public Chromosome(int n) {
    this(Generation.random.ints(0, n).distinct().limit(n).toArray());
  }

  public Chromosome(int[] genes) {
    this.n = genes.length;
    if (Arrays.stream(genes).anyMatch(gene -> gene < 0 || gene >= n)) {
      throw new IllegalArgumentException("Invalid gene value");
    }
    this.genes = genes;
    this.points = Arrays.stream(genes).mapToObj(data::getPoint).toList();
  }

  public int[] getGenes() {
    return genes;
  }

  public double fitness() {
    if (fitness == null) {
      fitness = getFitness();
    }
    return fitness;
  }

  private double getFitness() {
    List<Double> distances = new ArrayList<>();
    for (int i = 0; i < points.size(); i++) {
      distances.add(points.get(i).distanceTo(points.get((i + 1) % points.size())));
    }
    return distances.stream().reduce(0.0, Double::sum);
  }

  public void mutate1(double mutationRate) {
    for (int i = 0; i < genes.length; i++) {
      if (Generation.random.nextDouble() < mutationRate) {
        int j = Generation.random.nextInt(genes.length);
        int temp = genes[i];
        genes[i] = genes[j];
        genes[j] = temp;
      }
    }
  }

  public void mutate2(double mutationRate) {
    int index1 = Generation.random.nextInt(genes.length);
    int index2 = Generation.random.nextInt(genes.length);

    int temp = genes[index1];
    genes[index1] = genes[index2];
    genes[index2] = temp;
  }

  @Override
  public String toString() {
    return Arrays.toString(genes);
  }
}
