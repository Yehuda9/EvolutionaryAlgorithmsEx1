package EightQueenPuzzle;

import Common.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Puzzles extends Generation {

  public Puzzles(int generationSize, int chromosomeSize, Random random) {
    this(
        Stream.generate(() -> new Puzzle(chromosomeSize, random))
            .limit(generationSize)
            .collect(Collectors.toList()),
        random);
  }

  public Puzzles(Random random) {
    this(new ArrayList<>(), random);
  }

  private Puzzles(List<Chromosome> chromosomes, Random random) {
    super(chromosomes, random);
  }

  @Override
  protected Generation emptyGeneration() {
    return new Puzzles(random);
  }

  @Override
  protected Chromosome newChromosome(Gene[] genes, Random random) {
    return new Puzzle(genes, random);
  }

  @Override
  protected Chromosome getFittest() {
    return chromosomes.stream().max(Comparator.comparing(Chromosome::fitness)).orElseThrow();
  }

  @Override
  protected List<Chromosome> getFittest(int limit) {
    return chromosomes.stream()
        .sorted(Comparator.comparing(Chromosome::fitness).reversed())
        .limit(limit)
        .toList();
  }
}
