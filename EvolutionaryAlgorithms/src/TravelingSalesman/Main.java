package TravelingSalesman;

import Common.Config;
import Common.Evolution;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Data.init(args[0]);

    Thread thread1 =
        new Thread(
            () ->
                new Evolution()
                    .tryRunEvolution(
                        Config.ProblemType.TravelingSalesman,
                        /* generationSize= */ 3500,
                        /* chromosomeSize= */ 48,
                        /* mutationRate= */ 0.12,
                        Config.SelectionType.Tournament,
                        Config.CrossoverType.TwoPoints,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 1000));

    Evolution.runParallel(thread1);
  }
}
