import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generation {
  private static final long SEED = 3447761810369037120L;
  public static final Random random = new Random(SEED);
  private final List<Chromosome> chromosomes;

  public Generation(int generationSize, int chromosomeSize) {
    chromosomes =
        Stream.generate(() -> new Chromosome(chromosomeSize))
            .limit(generationSize)
            .collect(Collectors.toList());
  }

  public Generation() {
    this.chromosomes = new ArrayList<>();
  }

  public Chromosome getFittest() {
    return chromosomes.stream().min(Comparator.comparing(Chromosome::fitness)).orElseThrow();
  }

  public double getAverageFitness() {
    return chromosomes.stream().mapToDouble(Chromosome::fitness).average().orElseThrow();
  }

  public List<Chromosome> getFittest(int limit) {
    return chromosomes.stream()
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
    double totalFitness =
        chromosomes.stream().mapToDouble(Chromosome::fitness).map(d -> 1 / d).sum();
    double random = Generation.random.nextDouble() * totalFitness;
    double sum = 0;
    for (Chromosome chromosome : chromosomes) {
      sum += 1 / chromosome.fitness();
      if (sum >= random) {
        return chromosome;
      }
    }
    return chromosomes.get(chromosomes.size() - 1);
  }

  private static Pair<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
    int n = Math.max(parent1.n, parent2.n);
    int crossoverPoint = random.nextInt(n);
    List<Integer> genes1 =
        new ArrayList<>(Arrays.stream(parent1.getGenes(), 0, crossoverPoint).boxed().toList());
    genes1.addAll(
        Arrays.stream(parent2.getGenes()).filter(i -> !genes1.contains(i)).boxed().toList());

    List<Integer> genes2 =
        new ArrayList<>(Arrays.stream(parent2.getGenes(), 0, crossoverPoint).boxed().toList());
    genes2.addAll(
        Arrays.stream(parent1.getGenes()).filter(i -> !genes2.contains(i)).boxed().toList());

    return new Pair<>(
        new Chromosome(genes1.stream().mapToInt(Integer::intValue).toArray()),
        new Chromosome(genes2.stream().mapToInt(Integer::intValue).toArray()));
  }

  public Generation getNextGeneration1(int generationSize, double mutationRate, int elitism) {
    Generation nextGeneration = new Generation();

    this.getFittest(elitism).forEach(nextGeneration::add);
    while (nextGeneration.size() < generationSize) {
      Chromosome parent1 = select();
      Chromosome parent2 = select();
      Pair<Chromosome> children = crossover(parent1, parent2);
      children.first().mutate2(mutationRate);
      children.second().mutate2(mutationRate);
      nextGeneration.add(children.first());
      nextGeneration.add(children.second());

      /*if (children.first().fitness() <= Math.min(parent1.fitness(), parent2.fitness())) {
        nextGeneration.add(children.first());
      }
      if (children.second().fitness() <= Math.min(parent1.fitness(), parent2.fitness())) {
        nextGeneration.add(children.second());
      }*/
    }

    return nextGeneration;
  }

  public Generation getNextGeneration2(
      int generationSize, int chromosomeSize, double mutationRate, int elitism) {
    int k = (int) (chromosomeSize * (1 / 3.0));
    Generation nextGeneration = new Generation(generationSize, chromosomeSize);

    this.getFittest(elitism).forEach(nextGeneration::add);
    while (nextGeneration.size() < generationSize) {

      List<Chromosome> parents = new ArrayList<>(chromosomes);
      Collections.shuffle(parents);
      List<Chromosome> selectedParents =
          parents.stream()
              .limit(k)
              .sorted(Comparator.comparing(Chromosome::fitness))
              .limit(2)
              .toList();

      Pair<Chromosome> children = crossover(selectedParents.get(0), selectedParents.get(1));
      children.first().mutate2(mutationRate);
      children.second().mutate2(mutationRate);
      nextGeneration.add(children.first());
      nextGeneration.add(children.second());
    }

    return nextGeneration;
  }
}
