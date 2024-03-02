import java.util.Arrays;
import java.util.Random;

public class Chromosome {
  public static final int N = 8;
  private static final int MAX_FITNESS = 28;
  private final int[] genes;

  public Chromosome() {
    this(new Random().ints(0, N).limit(N).toArray());
  }

  public int[] getGenes() {
    return genes;
  }

  public Chromosome(int[] genes) {
    if (genes.length != N) {
      throw new IllegalArgumentException("Invalid genes length");
    }
    if (Arrays.stream(genes).anyMatch(gene -> gene < 0 || gene >= N)) {
      throw new IllegalArgumentException("Invalid gene value");
    }
    this.genes = genes;
  }

  public int fitness() {
    int fitness = 0;
    for (int i = 0; i < N; i++) {
      for (int j = i + 1; j < N; j++) {
        if (genes[i] == genes[j] || Math.abs(i - j) == Math.abs(genes[i] - genes[j])) {
          fitness++;
        }
      }
    }
    return MAX_FITNESS - fitness;
  }

  public void mutate(double mutationRate) {
    for (int i = 0; i < N; i++) {
      if (Math.random() < mutationRate) {
        genes[i] = new Random().nextInt(N);
      }
    }
  }

  // print the board
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        sb.append(genes[i] == j ? "Q " : "* ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
