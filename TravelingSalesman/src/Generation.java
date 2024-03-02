import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generation {
  private static final long SEED = 1709399744310L;
  public static final Random random = new Random(SEED);
  private final List<Chromosome> chromosomes;

  public Generation(int generationSize, int chromosomeSize) {
    chromosomes =
        Stream.generate(() -> new Chromosome(chromosomeSize))
            .parallel()
            .limit(generationSize)
            .collect(Collectors.toList());
  }

  public Generation() {
    this.chromosomes = new ArrayList<>();
  }

  public Chromosome getFittest() {
    return chromosomes.stream()
        .parallel()
        .min(Comparator.comparing(Chromosome::fitness))
        .orElseThrow();
  }

  public double getAverageFitness() {
    return chromosomes.stream().parallel().mapToDouble(Chromosome::fitness).average().orElseThrow();
  }

  public List<Chromosome> getFittest(int limit) {
    return chromosomes.stream()
        .parallel()
        .sorted(Comparator.comparing(Chromosome::fitness))
        .limit(limit)
        .toList();
  }

  public void add(Chromosome chromosome) {
    chromosomes.add(chromosome);
  }

  public int size() {
    return chromosomes.size();
  }

  private Chromosome select() {
    double totalFitness = chromosomes.stream().parallel().mapToDouble(Chromosome::fitness).sum();
    double random = Generation.random.nextDouble() * totalFitness;
    double sum = 0;
    for (Chromosome chromosome : chromosomes) {
      sum += chromosome.fitness();
      if (sum >= random) {
        return chromosome;
      }
    }
    return chromosomes.get(chromosomes.size() - 1);
  }

  private static Pair<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
    int n = Math.max(parent1.n, parent2.n);
    int[] genes1 = new int[n];
    int[] genes2 = new int[n];
    int crossoverPoint = random.nextInt(n);
    for (int i = 0; i < n; i++) {
      genes1[i] = i <= crossoverPoint ? parent1.getGenes()[i] : parent2.getGenes()[i];
      genes2[i] = i <= crossoverPoint ? parent2.getGenes()[i] : parent1.getGenes()[i];
    }
    return new Pair<>(new Chromosome(genes1), new Chromosome(genes2));
  }

  public Generation getNextGeneration(
      int generationSize, int chromosomeSize, double mutationRate, int elitism) {
    Generation nextGeneration = new Generation(generationSize, chromosomeSize);

    this.getFittest(elitism).forEach(nextGeneration::add);
    while (nextGeneration.size() < generationSize) {
      Chromosome parent1 = select();
      Chromosome parent2 = select();
      Pair<Chromosome> children = crossover(parent1, parent2);
      children.first().mutate1(mutationRate);
      children.second().mutate1(mutationRate);
      nextGeneration.add(children.first());
      nextGeneration.add(children.second());
    }

    return nextGeneration;
  }
}
