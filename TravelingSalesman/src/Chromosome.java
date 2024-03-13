import java.util.Arrays;
import java.util.List;

public class Chromosome {
  private static final Data data = Data.getInstance();

  private final int[] genes;

  public Chromosome(int n) {
    this(Generation.random.ints(0, n).distinct().limit(n).toArray());
  }

  public Chromosome(int[] genes) {
    if (Arrays.stream(genes).anyMatch(gene -> gene < 0 || gene >= genes.length)) {
      throw new IllegalArgumentException("Invalid gene value");
    }
    if (Arrays.stream(genes).distinct().count() != genes.length) {
      throw new IllegalArgumentException("Duplicate gene value");
    }
    this.genes = genes;
  }

  public int[] getGenes() {
    return genes;
  }

  public int size() {
    return genes.length;
  }

  public double fitness() {
    List<Point> points = Arrays.stream(genes).mapToObj(data::getPoint).toList();
    double distance = 0;
    for (int i = 0; i < points.size(); i++) {
      distance += points.get(i).distanceTo(points.get((i + 1) % points.size()));
    }
    return distance;
  }

  public void mutate(double mutationRate) {
    if (Generation.random.nextDouble() < mutationRate) {
      while (true) {
        int index1 = Generation.random.nextInt(genes.length);
        int index2 = Generation.random.nextInt(genes.length);

        if (index1 != index2) {
          int temp = genes[index1];
          genes[index1] = genes[index2];
          genes[index2] = temp;

          break;
        }
      }
    }
  }

  @Override
  public String toString() {
    return Arrays.toString(genes);
  }
}
