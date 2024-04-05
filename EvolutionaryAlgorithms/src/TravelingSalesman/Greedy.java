package TravelingSalesman;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Greedy {

  public static Point[] solve() {
    int n = Data.getInstance().size();
    Point[] points =
        IntStream.range(0, n).mapToObj(Data.getInstance()::getPoint).toArray(Point[]::new);
    Point[] bestPath = new Point[n];
    double bestDistance = Double.MAX_VALUE;

    for (Point point : points) {
      Point[] path = new Point[n];
      Set<Point> visited = new HashSet<>();
      path[0] = point;
      visited.add(point);
      for (int j = 1; j < n; j++) {
        Point last = path[j - 1];
        Point next = null;
        double minDistance = Double.MAX_VALUE;
        for (Point p : points) {
          if (!visited.contains(p)) {
            double distance = last.distanceTo(p);
            if (distance < minDistance) {
              minDistance = distance;
              next = p;
            }
          }
        }
        path[j] = next;
        visited.add(next);
      }

      double distance = 0;
      for (int j = 0; j < n; j++) {
        distance += path[j].distanceTo(path[(j + 1) % n]);
      }

      if (distance < bestDistance) {
        bestDistance = distance;
        System.arraycopy(path, 0, bestPath, 0, n);
      }
    }

    return bestPath;
  }

  private static void printPath(Point[] points) {
    int[] path = Arrays.stream(points).mapToInt(Data.getInstance()::getIndexOf).toArray();
    for (int k = 0; k < path.length; k++) {
      System.out.print(path[k] + 1);
      if (k < path.length - 1) {
        System.out.print(" -> ");
      }
    }
    System.out.println();
  }

  private static void printDistance(Point[] points) {
    double distance = 0;
    for (int i = 0; i < points.length; i++) {
      distance += points[i].distanceTo(points[(i + 1) % points.length]);
    }
    System.out.println("Distance: " + distance);
  }

  private static boolean isCorrect(Point[] points) {
    int[] path = Arrays.stream(points).mapToInt(Data.getInstance()::getIndexOf).toArray();

    if (path.length != Data.getInstance().size()) {
      return false;
    }

    if (Arrays.stream(path).distinct().count() != path.length) {
      return false;
    }

    for (int j : path) {
      if (j < 0 || j >= Data.getInstance().size()) {
        return false;
      }
    }

    return true;
  }

  public static void main(String[] args) {
    Data.init(args[0]);
    Point[] path = solve();
    printPath(path);
    printDistance(path);

    if (!isCorrect(path)) {
      System.out.println("Incorrect path");
    }
  }
}
