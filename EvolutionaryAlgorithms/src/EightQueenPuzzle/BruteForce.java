package EightQueenPuzzle;

import Common.Chromosome;
import Common.Gene;
import java.util.Random;

public class BruteForce {
  public static void main(String[] args) {
    int n = 8;
    Gene[] board;
    long start = System.currentTimeMillis();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          for (int l = 0; l < n; l++) {
            for (int m = 0; m < n; m++) {
              for (int o = 0; o < n; o++) {
                for (int p = 0; p < n; p++) {
                  for (int q = 0; q < n; q++) {
                    board =
                        new Gene[] {
                          new Row(i),
                          new Row(j),
                          new Row(k),
                          new Row(l),
                          new Row(m),
                          new Row(o),
                          new Row(p),
                          new Row(q)
                        };
                    Chromosome chromosome = new Puzzle(board, new Random());

                    if (chromosome.fitness() == 28) {
                      System.out.println("Solution found:");
                      System.out.println(chromosome);
                      System.out.println(
                          "Time taken: " + (System.currentTimeMillis() - start) + "ms");
                      return;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
