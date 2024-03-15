package TravelingSalesman;

import Common.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {

  private static Data instance = null;
  private final List<Point> points;

  private Data(String path) {
    List<Point> p;
    try {
      p = readPoints(path);
    } catch (IOException e) {
      p = new ArrayList<>();
    }
    points = p;
  }

  public static Data getInstance(String path) {
    if (instance == null) {
      instance = new Data(path);
    }
    return instance;
  }

  public static Data getInstance() {
    return instance;
  }

  public static void init(String path) {
    instance = new Data(path);
  }

  private static List<Point> readPoints(String path) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line;
    List<Point> points = new ArrayList<>();
    while ((line = reader.readLine()) != null) {
      String[] parts = line.strip().split("\\s+");
      points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
    }

    return points;
  }

  public Point getPoint(int index) {
    return points.get(index);
  }

  public int getIndexOf(Point point) {
    return points.indexOf(point);
  }

  public int size() {
    return points.size();
  }
}
