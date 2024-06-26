package Common;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Generation {

  protected final Random random;
  protected final List<Chromosome> chromosomes;

  public Generation(List<Chromosome> chromosomes, Random random) {
    this.chromosomes = chromosomes;
    this.random = random;
  }

  public Generation getNextGeneration(
      int generationSize,
      double mutationRate,
      Config.SelectionType selectionType,
      Config.CrossoverType crossoverType,
      double crossoverRate,
      int elitism) {
    Generation nextGeneration = emptyGeneration();

    this.getFittest(elitism).forEach(nextGeneration::add);
    while (nextGeneration.size() < generationSize) {
      Chromosome parent1 = select(selectionType);
      Chromosome parent2 = select(selectionType);
      Pair<Chromosome> children = crossover(parent1, parent2, crossoverType, crossoverRate);

      nextGeneration.add(children.first().mutate(mutationRate));
      nextGeneration.add(children.second().mutate(mutationRate));
    }

    return nextGeneration;
  }

  protected abstract Generation emptyGeneration();

  protected abstract Chromosome newChromosome(Gene[] genes, Random random);

  protected abstract Chromosome getFittest();

  protected abstract List<Chromosome> getFittest(int limit);

  public double getAverage() {
    return chromosomes.stream().mapToDouble(Chromosome::fitness).average().orElseThrow();
  }

  public void add(Chromosome chromosome) {
    chromosomes.add(chromosome);
  }

  private Pair<Chromosome> crossover(
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

  private Pair<Chromosome> singlePointCrossover(Chromosome parent1, Chromosome parent2) {
    int childSize = Math.min(parent1.size(), parent2.size());
    int crossoverPoint = random.nextInt(childSize);
    List<Gene> genes1 =
        new ArrayList<>(Arrays.stream(parent1.getGenes(), 0, crossoverPoint).toList());
    genes1.addAll(
        Arrays.stream(parent2.getGenes())
            .filter(i -> !genes1.contains(i))
            .limit(childSize - genes1.size())
            .toList());

    List<Gene> genes2 =
        new ArrayList<>(Arrays.stream(parent2.getGenes(), 0, crossoverPoint).toList());
    genes2.addAll(
        Arrays.stream(parent1.getGenes())
            .filter(i -> !genes2.contains(i))
            .limit(childSize - genes2.size())
            .toList());

    return new Pair<>(
        newChromosome(genes1.toArray(Gene[]::new), this.random),
        newChromosome(genes2.toArray(Gene[]::new), this.random));
  }

  private Pair<Chromosome> twoPointsCrossover(Chromosome parent1, Chromosome parent2) {
    int crossoverPoint1 = random.nextInt(parent1.size());
    int crossoverPoint2 = random.nextInt(parent2.size());
    while (crossoverPoint1 == crossoverPoint2) {
      crossoverPoint1 = random.nextInt(parent1.size());
      crossoverPoint2 = random.nextInt(parent2.size());
    }

    int start = Math.min(crossoverPoint1, crossoverPoint2);
    int end = Math.max(crossoverPoint1, crossoverPoint2);

    List<Gene> parent1Genes = Arrays.stream(parent1.getGenes()).toList();
    List<Gene> parent2Genes = Arrays.stream(parent2.getGenes()).toList();

    List<Gene> child11 = new ArrayList<>(parent1Genes.subList(0, start));
    List<Gene> child12 = new ArrayList<>(parent1Genes.subList(end, parent2Genes.size()));
    List<Gene> child1 = new ArrayList<>(Stream.concat(child11.stream(), child12.stream()).toList());
    List<Gene> child2 = new ArrayList<>(parent2Genes.subList(start, end));

    List<Gene> child1Remain = parent2Genes.stream().filter(i -> !child1.contains(i)).toList();
    List<Gene> child2Remain = parent1Genes.stream().filter(i -> !child2.contains(i)).toList();

    child1.clear();
    child1.addAll(child11);
    child1.addAll(child1Remain);
    child1.addAll(child12);
    child2.addAll(child2Remain);

    return new Pair<>(
        newChromosome(child1.toArray(Gene[]::new), this.random),
        newChromosome(child2.toArray(Gene[]::new), this.random));
  }

  private Chromosome select(Config.SelectionType selectionType) {
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

  private Chromosome select(Map<Chromosome, Double> chromosomeByProbability) {
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
