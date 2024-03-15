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
                        /* generationSize= */ 3500,
                        /* chromosomeSize= */ 48,
                        /* mutationRate= */ 0.12,
                        Config.SelectionType.Tournament,
                        Config.CrossoverType.TwoPoints,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 1000));

    Thread thread2 =
        new Thread(
            () ->
                new Evolution()
                    .tryRunEvolution(
                        /* generationSize= */ 3500,
                        /* chromosomeSize= */ 48,
                        /* mutationRate= */ 0.12,
                        Config.SelectionType.Tournament,
                        Config.CrossoverType.SinglePoint,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 1000));

    Thread thread3 =
        new Thread(
            () ->
                new Evolution()
                    .tryRunEvolution(
                        /* generationSize= */ 3500,
                        /* chromosomeSize= */ 48,
                        /* mutationRate= */ 0.12,
                        Config.SelectionType.Rank,
                        Config.CrossoverType.TwoPoints,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 1000));

    Thread thread4 =
        new Thread(
            () ->
                new Evolution()
                    .tryRunEvolution(
                        /* generationSize= */ 3500,
                        /* chromosomeSize= */ 48,
                        /* mutationRate= */ 0.12,
                        Config.SelectionType.Rank,
                        Config.CrossoverType.SinglePoint,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 1000));

    Thread thread5 =
        new Thread(
            () ->
                new Evolution()
                    .tryRunEvolution(
                        /* generationSize= */ 3500,
                        /* chromosomeSize= */ 48,
                        /* mutationRate= */ 0.12,
                        Config.SelectionType.RouletteWheel,
                        Config.CrossoverType.TwoPoints,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 1000));

    Thread thread6 =
        new Thread(
            () ->
                new Evolution()
                    .tryRunEvolution(
                        /* generationSize= */ 3500,
                        /* chromosomeSize= */ 48,
                        /* mutationRate= */ 0.12,
                        Config.SelectionType.RouletteWheel,
                        Config.CrossoverType.SinglePoint,
                        /* crossoverRate= */ 1,
                        /* elitism= */ 2,
                        /* maxGenerations= */ 1000));

    ExecutorService executor =
        Executors.newFixedThreadPool(
            (int) (Runtime.getRuntime().availableProcessors() * (3 / 4.0)));
    List<Thread> threads = List.of(thread1, thread2, thread3, thread4, thread5, thread6);

    for (Thread thread : threads) {
      executor.execute(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
    executor.shutdown();
  }
}
