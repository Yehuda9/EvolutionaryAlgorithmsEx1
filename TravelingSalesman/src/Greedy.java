
public class Greedy {

  public static double travelingSalesMan() {
    Data data = Data.getInstance();
    int[] path = new int[data.size()];
    boolean[] visited = new boolean[data.size()];
    path[0] = 0;
    visited[0] = true;
    for (int i = 1; i < data.size(); i++) {
      int next = -1;
      double min = Double.MAX_VALUE;
      for (int j = 0; j < data.size(); j++) {
        if (!visited[j] && data.getPoint(path[i - 1]).distanceTo(data.getPoint(j)) < min) {
          min = data.getPoint(path[i - 1]).distanceTo(data.getPoint(j));
          next = j;
        }
      }
      path[i] = next;
      visited[next] = true;
    }
    double distance = 0;
    for (int i = 0; i < path.length - 1; i++) {
      distance += data.getPoint(path[i]).distanceTo(data.getPoint(path[i + 1]));
    }
    distance += data.getPoint(path[path.length - 1]).distanceTo(data.getPoint(path[0]));
    return distance;
  }

  public static void main(String[] args) {
    System.out.println(travelingSalesMan());
  }
}
