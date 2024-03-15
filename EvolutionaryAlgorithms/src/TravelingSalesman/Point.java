package TravelingSalesman;

import Common.Gene;

public record Point(int x, int y) implements Gene {

  public double distanceTo(Point other) {
    return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
  }
}
