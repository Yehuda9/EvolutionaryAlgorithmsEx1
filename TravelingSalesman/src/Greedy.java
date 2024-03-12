import java.util.Arrays;

public class Greedy {

  public static int[] travelingSalesMan(int start) {
    Data data = Data.getInstance();
    int[] path = new int[data.size()];
    boolean[] visited = new boolean[data.size()];
    path[0] = 0;
    visited[0] = true;
    for (int i = start; i < data.size() + start - 1; i++) {
      int next = -1;
      double min = Double.MAX_VALUE;
      for (int j = 0; j < data.size(); j++) {
        if (!visited[j]
            && data.getPoint(path[(i - 1) % data.size()]).distanceTo(data.getPoint(j)) < min) {
          min = data.getPoint(path[(i - 1) % data.size()]).distanceTo(data.getPoint(j));
          next = j;
        }
      }
      path[i % data.size()] = next;
      visited[next] = true;
    }
    return path;
  }

  private static double distance(int[] path) {
    double distance = 0;
    for (int i = 0; i < path.length; i++) {
      distance +=
          Data.getInstance()
              .getPoint(path[i])
              .distanceTo(Data.getInstance().getPoint(path[(i + 1) % path.length]));
    }
    return distance;
  }

  public static void main(String[] args) {
    Data.init(args[0]);
    double min = Double.MAX_VALUE;
    for (int i = 1; i < Data.getInstance().size(); i++) {
      int[] path = travelingSalesMan(i);
      System.out.println(Arrays.toString(path));
      double distance = distance(path);
      if (distance < min) {
        min = distance;
      }
    }

    System.out.println(min);
  }
}
