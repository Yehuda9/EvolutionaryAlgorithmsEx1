package Common;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Generation {

  protected final Random random;
  protected final List<Chromosome> chromosomes;

  public Generation(List<Chromosome> chromosomes, Random random) {
    this.chromosomes = chromosomes;
    this.random = random;
  }

  public abstract Generation getNextGeneration(
      int generationSize,
      double mutationRate,
      Config.SelectionType selectionType,
      Config.CrossoverType crossoverType,
      double crossoverRate,
      int elitism);

  protected abstract Chromosome getFittest();

  protected abstract double getAverage();

  protected abstract List<Chromosome> getFittest(int limit);

  public void add(Chromosome chromosome) {
    chromosomes.add(chromosome);
  }

  protected Pair<Chromosome> crossover(
      Chromosome parent1,
      Chromosome parent2,
      Config.CrossoverType crossoverType,
      double crossoverRate) {
    if (random.nextDouble() > crossoverRate) {
      return new Pair<>(parent1, parent2);
    }
    return switch (crossoverType) {
      case SinglePoint -> singlePointCrossover(parent1, parent2);
      case TwoPoints -> twoPointsCrossover(parent1, parent2);
    };
  }

  protected abstract Pair<Chromosome> singlePointCrossover(Chromosome parent1, Chromosome parent2);

  protected abstract Pair<Chromosome> twoPointsCrossover(Chromosome parent1, Chromosome parent2);

  protected Chromosome select(Config.SelectionType selectionType) {
    return switch (selectionType) {
      case RouletteWheel -> rouletteWheelSelection();
      case Tournament -> tournamentSelection();
      case Rank -> rankSelection();
    };
  }

  private Chromosome rouletteWheelSelection() {
    List<Double> fitnesses =
        chromosomes.stream()
            .parallel()
            .mapToDouble(Chromosome::fitness)
            .map(d -> 1 / d)
            .boxed()
            .toList();

    return select(
        IntStream.range(0, chromosomes.size())
            .boxed()
            .collect(Collectors.toMap(chromosomes::get, fitnesses::get)));
  }

  private Chromosome rankSelection() {
    double sp = 2;

    List<Chromosome> chromosomes =
        this.chromosomes.stream().sorted(Comparator.comparing(Chromosome::fitness)).toList();

    List<Double> probabilities =
        IntStream.range(0, chromosomes.size())
            // https://en.wikipedia.org/wiki/Selection_(genetic_algorithm)#Rank_Selection
            .mapToObj(i -> (sp - ((2 * sp - 2) * ((i - 1) / (double) (size() - 1)))) / size())
            .toList();

    return select(
        IntStream.range(0, chromosomes.size())
            .boxed()
            .collect(Collectors.toMap(chromosomes::get, probabilities::get)));
  }

  private Chromosome tournamentSelection() {
    int tournamentSize = 4;
    List<Chromosome> tournament = new ArrayList<>();
    IntStream.range(0, tournamentSize)
        .forEach(i -> tournament.add(chromosomes.get(random.nextInt(chromosomes.size()))));
    return tournament.stream().min(Comparator.comparing(Chromosome::fitness)).orElseThrow();
  }

  protected Chromosome select(Map<Chromosome, Double> chromosomeByProbability) {
    double probabilitiesSum = chromosomeByProbability.values().stream().reduce(0.0, Double::sum);
    double r = random.nextDouble() * probabilitiesSum;
    double sum = 0;
    for (Map.Entry<Chromosome, Double> entry : chromosomeByProbability.entrySet()) {
      sum += entry.getValue();
      if (sum >= r) {
        return entry.getKey();
      }
    }
    return chromosomeByProbability.entrySet().stream()
        .skip(chromosomeByProbability.size() - 1)
        .findFirst()
        .orElseThrow()
        .getKey();
  }

  public int size() {
    return chromosomes.size();
  }
}
