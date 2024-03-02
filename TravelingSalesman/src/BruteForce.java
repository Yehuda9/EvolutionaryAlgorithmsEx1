import java.util.ArrayList;
import java.util.List;

public class BruteForce {


  // implementation of traveling
  // Salesman Problem
  static double travellingSalesmanProblem(double[][] graph, int chromosomeSize) {
    // store all vertex apart
    // from source vertex
    List<Integer> vertex = new ArrayList<>();
    int s = 0;
    for (int i = 0; i < chromosomeSize; i++) {
      if (i != s) {
        vertex.add(i);
      }
    }

    // store minimum weight
    // Hamiltonian Cycle.
    double min_path = Double.MAX_VALUE;
    do {
      // store current Path weight(cost)
      double current_pathweight = 0;

      // compute current path weight
      int k = s;

      for (Integer integer : vertex) {
        current_pathweight += graph[k][integer];
        k = integer;
      }
      current_pathweight += graph[k][s];

      // update minimum
      min_path = Math.min(min_path, current_pathweight);

    } while (findNextPermutation(vertex));

    return min_path;
  }

  // Function to swap the data
  // present in the left and right indices
  public static void swap(List<Integer> data, int left, int right) {
    // Swap the data
    int temp = data.get(left);
    data.set(left, data.get(right));
    data.set(right, temp);

    // Return the updated array
  }

  // Function to reverse the sub-array
  // starting from left to the right
  // both inclusive
  public static void reverse(List<Integer> data, int left, int right) {
    // Reverse the sub-array
    while (left < right) {
      int temp = data.get(left);
      data.set(left++, data.get(right));
      data.set(right--, temp);
    }

    // Return the updated array
  }

  // Function to find the next permutation
  // of the given integer array
  public static boolean findNextPermutation(List<Integer> data) {
    // If the given dataset is empty
    // or contains only one element
    // next_permutation is not possible
    if (data.size() <= 1) {
      return false;
    }

    int last = data.size() - 2;

    // find the longest non-increasing
    // suffix and find the pivot
    while (last >= 0) {
      if (data.get(last) < data.get(last + 1)) {
        break;
      }
      last--;
    }

    // If there is no increasing pair
    // there is no higher order permutation
    if (last < 0) {
      return false;
    }

    int nextGreater = data.size() - 1;

    // Find the rightmost successor
    // to the pivot
    for (int i = data.size() - 1; i > last; i--) {
      if (data.get(i) > data.get(last)) {
        nextGreater = i;
        break;
      }
    }

    // Swap the successor and
    // the pivot
    swap(data, nextGreater, last);

    // Reverse the suffix
    reverse(data, last + 1, data.size() - 1);

    // Return true as the
    // next_permutation is done
    return true;
  }

  // Driver Code
  public static void main(String[] args) {
    int chromosomeSize = Integer.parseInt(args[0]);
    // matrix representation of graph

    Data data = Data.getInstance();
    double[][] graph = new double[chromosomeSize][chromosomeSize];
    for (int i = 0; i < chromosomeSize; i++) {
      for (int j = 0; j < chromosomeSize; j++) {
        graph[i][j] = data.getPoint(i).distanceTo(data.getPoint(j));
      }
    }

    System.out.println(travellingSalesmanProblem(graph, chromosomeSize));
  }
}
