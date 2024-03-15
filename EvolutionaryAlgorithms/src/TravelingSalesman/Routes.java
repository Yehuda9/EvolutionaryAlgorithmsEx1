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

  private Routes(List<Chromosome> chromosomes, Random random) {
    super(chromosomes, random);
  }

  @Override
  protected Generation emptyGeneration() {
    return new Routes(random);
  }

  @Override
  protected Chromosome newChromosome(Gene[] genes, Random random) {
    return new Route(genes, random);
  }

  @Override
  protected Chromosome getFittest() {
    return chromosomes.stream().min(Comparator.comparing(Chromosome::fitness)).orElseThrow();
  }

  @Override
  protected List<Chromosome> getFittest(int limit) {
    return chromosomes.stream()
        .sorted(Comparator.comparing(Chromosome::fitness))
        .limit(limit)
        .toList();
  }
}
