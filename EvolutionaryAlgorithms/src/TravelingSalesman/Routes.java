package TravelingSalesman;

import Common.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Routes extends Generation {

  public Routes(int generationSize, int chromosomeSize, Random random) {
    this(
        Stream.generate(() -> new Route(chromosomeSize, random))
            .limit(generationSize)
            .collect(Collectors.toList()),
        random);
  }

  public Routes(Random random) {
    this(new ArrayList<>(), random);
  }

  public Routes(List<Chromosome> chromosomes, Random random) {
    super(chromosomes, random);
  }

  public Generation getNextGeneration(
      int generationSize,
      double mutationRate,
      Config.SelectionType selectionType,
      Config.CrossoverType crossoverType,
      double crossoverRate,
      int elitism) {
    Generation nextGeneration = new Routes(this.random);

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

  @Override
  protected Chromosome getFittest() {
    return chromosomes.stream().min(Comparator.comparing(Chromosome::fitness)).orElseThrow();
  }

  @Override
  protected double getAverage() {
    return chromosomes.stream().mapToDouble(Chromosome::fitness).average().orElseThrow();
  }

  @Override
  protected List<Chromosome> getFittest(int limit) {
    return chromosomes.stream()
        .sorted(Comparator.comparing(Chromosome::fitness))
        .limit(limit)
        .toList();
  }

  @Override
  protected Pair<Chromosome> twoPointsCrossover(Chromosome parent1, Chromosome parent2) {
    int crossoverPoint1 = random.nextInt(parent1.size());
    int crossoverPoint2 = random.nextInt(parent2.size());
    while (crossoverPoint1 == crossoverPoint2) {
      crossoverPoint1 = random.nextInt(parent1.size());
      crossoverPoint2 = random.nextInt(parent2.size());
    }

    int start = Math.min(crossoverPoint1, crossoverPoint2);
    int end = Math.max(crossoverPoint1, crossoverPoint2);

    List<Point> parent1Genes = Arrays.stream(parent1.getGenes()).toList();
    List<Point> parent2Genes = Arrays.stream(parent2.getGenes()).toList();

    List<Point> child11 = new ArrayList<>(parent1Genes.subList(0, start));
    List<Point> child12 = new ArrayList<>(parent1Genes.subList(end, parent2Genes.size()));
    List<Point> child1 =
        new ArrayList<>(Stream.concat(child11.stream(), child12.stream()).toList());
    List<Point> child2 = new ArrayList<>(parent2Genes.subList(start, end));

    List<Point> child1Remain = parent2Genes.stream().filter(i -> !child1.contains(i)).toList();
    List<Point> child2Remain = parent1Genes.stream().filter(i -> !child2.contains(i)).toList();

    child1.clear();
    child1.addAll(child11);
    child1.addAll(child1Remain);
    child1.addAll(child12);
    child2.addAll(child2Remain);

    return new Pair<>(
        new Route(child1.toArray(Point[]::new), this.random),
        new Route(child2.toArray(Point[]::new), this.random));
  }

  @Override
  protected Pair<Chromosome> singlePointCrossover(Chromosome parent1, Chromosome parent2) {
    int crossoverPoint = random.nextInt(Math.max(parent1.size(), parent2.size()));
    List<Point> genes1 =
        new ArrayList<>(Arrays.stream(parent1.getGenes(), 0, crossoverPoint).toList());
    genes1.addAll(Arrays.stream(parent2.getGenes()).filter(i -> !genes1.contains(i)).toList());

    List<Point> genes2 =
        new ArrayList<>(Arrays.stream(parent2.getGenes(), 0, crossoverPoint).toList());
    genes2.addAll(Arrays.stream(parent1.getGenes()).filter(i -> !genes2.contains(i)).toList());

    return new Pair<>(
        new Route(genes1.toArray(Point[]::new), this.random),
        new Route(genes2.toArray(Point[]::new), this.random));
  }

  @Override
  public int size() {
    return chromosomes.size();
  }
}
