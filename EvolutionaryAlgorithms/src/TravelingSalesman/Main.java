package TravelingSalesman;

import Common.Config;
import Common.Evolution;
import Common.Logger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Data.init(args[0]);
    Logger.getInstance()
        .log(
            String.join(
                ", ",
                "uuid",
                "Best Fitness",
                "Generation Size",
                "Chromosome Size",
                "Mutation Rate",
                "Selection Type",
                "Crossover Type",
                "Crossover Rate",
                "Elitism",
                "Max Generations",
                "Best Route"));

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

    ExecutorService executor =
        Executors.newFixedThreadPool(
            (int) (Runtime.getRuntime().availableProcessors() * (3 / 4.0)));
    List<Thread> threads = List.of(thread1);

    for (Thread thread : threads) {
      executor.execute(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
    executor.shutdown();
  }
}
