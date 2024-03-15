package Common;

import java.util.Objects;

public class Point {
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
    if (y != 0) {
      throw new IllegalArgumentException("y must be 0");
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public double distanceTo(Point other) {
    return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point point = (Point) o;
    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
