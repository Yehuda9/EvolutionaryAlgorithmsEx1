package EightQueenPuzzle;

import Common.*;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Puzzles extends Generation {

  public Puzzles(List<Chromosome> chromosomes, Random random) {
    super(chromosomes, random);
  }

  @Override
  public Generation getNextGeneration(
      int generationSize,
      double mutationRate,
      Config.SelectionType selectionType,
      Config.CrossoverType crossoverType,
      double crossoverRate,
      int elitism) {
    return null;
  }

  @Override
  protected Chromosome getFittest() {
    return chromosomes.stream().max(Comparator.comparing(Chromosome::fitness)).orElseThrow();
  }

  @Override
  protected double getAverage() {
    return chromosomes.stream().mapToDouble(Chromosome::fitness).average().orElseThrow();
  }

  @Override
  protected List<Chromosome> getFittest(int limit) {
    return chromosomes.stream()
        .sorted(Comparator.comparing(Chromosome::fitness).reversed())
        .limit(limit)
        .toList();
  }

  @Override
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
      case TwoPoints -> new Pair<>(parent1, parent2);
    };
  }

  @Override
  protected Pair<Chromosome> singlePointCrossover(Chromosome parent1, Chromosome parent2) {
    Point[] genes1 = new Point[size()];
    Point[] genes2 = new Point[size()];
    int crossoverPoint = new Random().nextInt(size());
    System.out.println(crossoverPoint);
    for (int i = 0; i < size(); i++) {
      genes1[i] = i <= crossoverPoint ? parent1.getGenes()[i] : parent2.getGenes()[i];
      genes2[i] = i <= crossoverPoint ? parent2.getGenes()[i] : parent1.getGenes()[i];
    }
    return new Pair<>(new Puzzle(genes1, random), new Puzzle(genes2, random));
  }

  @Override
  protected Pair<Chromosome> twoPointsCrossover(Chromosome parent1, Chromosome parent2) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
