import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Generation {
  private final List<Chromosome> chromosomes;

  public Generation(int size) {
    chromosomes = Stream.generate(Chromosome::new).parallel().limit(size).toList();
  }

  public Generation() {
    this.chromosomes = new ArrayList<>();
  }

  public Chromosome getFittest() {
    return chromosomes.stream()
        .parallel()
        .max(Comparator.comparing(Chromosome::fitness))
        .orElseThrow();
  }

  public void add(Chromosome chromosome) {
    chromosomes.add(chromosome);
  }

  public int size() {
    return chromosomes.size();
  }

  // Roulette Wheel Selection
  public Chromosome select() {
    double totalFitness = chromosomes.stream().parallel().mapToDouble(Chromosome::fitness).sum();
    double random = Math.random() * totalFitness;
    double sum = 0;
    for (Chromosome chromosome : chromosomes) {
      sum += chromosome.fitness();
      if (sum >= random) {
        return chromosome;
      }
    }
    return chromosomes.get(chromosomes.size() - 1);
  }

  public static Pair<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
    int[] genes1 = new int[Chromosome.N];
    int[] genes2 = new int[Chromosome.N];
    int crossoverPoint = new Random().nextInt(Chromosome.N);
    System.out.println(crossoverPoint);
    for (int i = 0; i < Chromosome.N; i++) {
      genes1[i] = i <= crossoverPoint ? parent1.getGenes()[i] : parent2.getGenes()[i];
      genes2[i] = i <= crossoverPoint ? parent2.getGenes()[i] : parent1.getGenes()[i];
    }
    return new Pair<>(new Chromosome(genes1), new Chromosome(genes2));
  }

  public Generation getNextGeneration(int generationSize, double mutationRate) {
    Generation nextGeneration = new Generation(generationSize);

    while (nextGeneration.size() < generationSize) {
      Chromosome parent1 = this.select();
      Chromosome parent2 = this.select();
      Pair<Chromosome> children = Generation.crossover(parent1, parent2);
      children.first().mutate(mutationRate);
      children.second().mutate(mutationRate);
      nextGeneration.add(children.first());
      nextGeneration.add(children.second());
    }

    return nextGeneration;
  }
}
