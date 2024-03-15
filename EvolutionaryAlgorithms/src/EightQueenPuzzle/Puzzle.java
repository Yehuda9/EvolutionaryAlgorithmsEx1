package EightQueenPuzzle;

import Common.Chromosome;
import Common.Point;
import java.util.Random;

public class Puzzle extends Chromosome {
  private static final int MAX_FITNESS = 28;
  private final Random random;

  private Double fitness = null;

  protected Puzzle(Point[] points, Random random) {
    super(points);
    this.random = random;
  }

  @Override
  public double fitness() {
    return fitness != null ? fitness : (fitness = getFitness());
  }

  private double getFitness() {
    int fitness = 0;
    for (Point point : getGenes()) {
      for (Point other : getGenes()) {
        if (point.equals(other)) {
          continue;
        }
        if (point.getX() != other.getX()
            && point.getY() != other.getY()
            && Math.abs(point.getX() - other.getX()) != Math.abs(point.getY() - other.getY())) {
          fitness++;
        }
      }
    }
    return MAX_FITNESS - fitness;
  }

  @Override
  public Chromosome mutate(double mutationRate) {
    Point[] newGenes = getGenes();
    if (random.nextDouble() < mutationRate) {
      int index = random.nextInt(newGenes.length);

      int oldY = newGenes[index].getY();
      int newY = random.nextInt(newGenes.length);
      while (newY == oldY) {
        newY = random.nextInt(newGenes.length);
      }

      newGenes[index] = new Point(newGenes[index].getX(), newY);
    }
    return new Puzzle(newGenes, random);
  }

  @Override
  public int size() {
    return getGenes().length;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        for (Point point : getGenes()) {
          sb.append(point.getX() == i && point.getY() == j ? "Q " : "* ");
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}
