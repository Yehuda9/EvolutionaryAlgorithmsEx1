package EightQueenPuzzle;

import java.util.ArrayList;
import java.util.List;

public class PuzzleTest {
  public static void main(String[] args) {
    List<Integer> queens = new ArrayList<>(10);

    for (int i = 0; i < 11; i++) {
      queens.add(i);
    }

    System.out.println(queens);
  }
}
