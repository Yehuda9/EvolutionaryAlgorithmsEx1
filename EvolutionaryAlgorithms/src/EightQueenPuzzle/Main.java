package EightQueenPuzzle;

import Common.Config;
import Common.Evolution;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Thread thread1 =
        new Thread(
            () ->
                new Evolution()
                    .tryRunEvolution(
                        Config.ProblemType.EightQueensPuzzle,
                        /* generationSize= */ 100,
                        /* chromosomeSize= */ 8,
                        /* mutationRate= */ 0.01,
                        Config.SelectionType.RouletteWheel,
                        Config.CrossoverType.SinglePoint,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 100));

    Evolution.runParallel(thread1);
  }
}
