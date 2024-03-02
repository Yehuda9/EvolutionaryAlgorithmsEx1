import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
  private static final String PATH = "../data/tsp.txt";

  private static Data instance = null;

  public static Data getInstance() {
    if (instance == null) {
      instance = new Data();
    }
    return instance;
  }

  private final List<Point> points;

  private Data() {
    List<Point> p;
    try {
      p = readPoints();
    } catch (IOException e) {
      p = new ArrayList<>();
    }
    points = p;
  }

  public Point getPoint(int index) {
    return points.get(index);
  }

  public int size() {
    return points.size();
  }

  private static List<Point> readPoints() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(PATH));
    String line;
    List<Point> points = new ArrayList<>();
    while ((line = reader.readLine()) != null) {
      String[] parts = line.strip().split("\\s+");
      points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
    }

    return points;
  }
}
