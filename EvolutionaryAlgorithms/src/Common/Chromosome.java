package Common;

import java.util.Arrays;

public abstract class Chromosome {

  private final Gene[] genes;

  protected Chromosome(Gene[] points) {
    this.genes = points;
  }

  public Gene[] getGenes() {
    return Arrays.copyOf(genes, genes.length);
  }

  public abstract double fitness();

  public abstract Chromosome mutate(double mutationRate);

  public int size() {
    return genes.length;
  }
}
