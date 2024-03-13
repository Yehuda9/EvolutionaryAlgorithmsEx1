import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

  private static Chromosome select(Map<Chromosome, Double> chromosomeByProbability) {
    double probabilitiesSum = chromosomeByProbability.values().stream().reduce(0.0, Double::sum);
    double random = Generation.random.nextDouble() * probabilitiesSum;
    double sum = 0;
    for (Map.Entry<Chromosome, Double> entry : chromosomeByProbability.entrySet()) {
      sum += entry.getValue();
      if (sum >= random) {
        return entry.getKey();
      }
    }
    return chromosomeByProbability.entrySet().stream()
        .skip(chromosomeByProbability.size() - 1)
        .findFirst()
        .orElseThrow()
        .getKey();
  }

  private static Pair<Chromosome> singlePointCrossover(Chromosome parent1, Chromosome parent2) {
    int crossoverPoint = random.nextInt(Math.max(parent1.size(), parent2.size()));
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

  private static Pair<Chromosome> mixedCrossover(Chromosome parent1, Chromosome parent2) {
    int crossoverPoint1 = random.nextInt(parent1.size());
    int crossoverPoint2 = random.nextInt(parent2.size());
    while (crossoverPoint1 == crossoverPoint2) {
      crossoverPoint1 = random.nextInt(parent1.size());
      crossoverPoint2 = random.nextInt(parent2.size());
    }

    int start = Math.min(crossoverPoint1, crossoverPoint2);
    int end = Math.max(crossoverPoint1, crossoverPoint2);

    List<Integer> parent1Genes = Arrays.stream(parent1.getGenes()).boxed().toList();
    List<Integer> parent2Genes = Arrays.stream(parent2.getGenes()).boxed().toList();

    List<Integer> child11 = new ArrayList<>(parent1Genes.subList(0, start));
    List<Integer> child12 = new ArrayList<>(parent1Genes.subList(end, parent2Genes.size()));
    List<Integer> child1 =
        new ArrayList<>(Stream.concat(child11.stream(), child12.stream()).toList());
    List<Integer> child2 = new ArrayList<>(parent2Genes.subList(start, end));

    List<Integer> child1Remain = parent2Genes.stream().filter(i -> !child1.contains(i)).toList();
    List<Integer> child2Remain = parent1Genes.stream().filter(i -> !child2.contains(i)).toList();

    child1.clear();
    child1.addAll(child11);
    child1.addAll(child1Remain);
    child1.addAll(child12);
    child2.addAll(child2Remain);

    return new Pair<>(
        new Chromosome(child1.stream().mapToInt(Integer::intValue).toArray()),
        new Chromosome(child2.stream().mapToInt(Integer::intValue).toArray()));
  }

  public Generation getNextGeneration1(int generationSize, double mutationRate, int elitism) {
    Generation nextGeneration = new Generation();

    this.getFittest(elitism).forEach(nextGeneration::add);
    while (nextGeneration.size() < generationSize) {
      Chromosome parent1 = rouletteWheelSelection();
      Chromosome parent2 = rouletteWheelSelection();
      Pair<Chromosome> children = singlePointCrossover(parent1, parent2);
      children.first().mutate(mutationRate);
      children.second().mutate(mutationRate);
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
      int generationSize, double mutationRate, double crossoverRate, int elitism) {
    Generation nextGeneration = new Generation();

    this.getFittest(elitism).forEach(nextGeneration::add);
    while (nextGeneration.size() < generationSize) {
      if (random.nextDouble() > crossoverRate) {
        continue;
      }
      Chromosome parent1 = rankSelection();
      Chromosome parent2 = rankSelection();
      Pair<Chromosome> children = singlePointCrossover(parent1, parent2);

      nextGeneration.add(children.first().mutate(mutationRate));
      nextGeneration.add(children.second().mutate(mutationRate));
    }

    return nextGeneration;
  }

  public Generation getNextGeneration3(
      int generationSize, double mutationRate, double crossoverRate, int elitism) {
    Generation nextGeneration = new Generation();

    this.getFittest(elitism).forEach(nextGeneration::add);
    while (nextGeneration.size() < generationSize) {
      if (random.nextDouble() > crossoverRate) {
        continue;
      }
      Chromosome parent1 = tournamentSelection();
      Chromosome parent2 = tournamentSelection();
      Pair<Chromosome> children = mixedCrossover(parent1, parent2);

      nextGeneration.add(children.first().mutate(mutationRate));
      nextGeneration.add(children.second()/*.mutate(mutationRate)*/);
    }

    return nextGeneration;
  }
}
