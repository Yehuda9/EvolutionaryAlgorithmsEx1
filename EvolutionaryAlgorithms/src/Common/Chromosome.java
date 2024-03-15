package Common;

import java.util.Arrays;

public abstract class Chromosome {

  private final Point[] genes;

  protected Chromosome(Point[] points) {
    this.genes = points;
  }

  public Point[] getGenes() {
    return Arrays.copyOf(genes, genes.length);
  }

  public abstract double fitness();

  public abstract Chromosome mutate(double mutationRate);

  public int size() {
    return genes.length;
  }
}
