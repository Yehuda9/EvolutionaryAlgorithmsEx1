import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  private static final long SEED = 3447761810369037120L;
  public static final Random random = new Random(SEED);

  public static void tryRunEvolution(
      int generationSize,
      int chromosomeSize,
      double mutationRate,
      double crossoverRate,
      int elitism,
      int maxGenerations) {
    try {
      runEvolution(
          generationSize, chromosomeSize, mutationRate, crossoverRate, elitism, maxGenerations);
    } catch (IOException e) {
      Logger.getInstance().log("Failed to run evolution: " + e.getMessage());
    }
  }

  public static void runEvolution(
      int generationSize,
      int chromosomeSize,
      double mutationRate,
      double crossoverRate,
      int elitism,
      int maxGenerations)
      throws IOException {
    String uuid = UUID.randomUUID().toString();

    DataLogger dataLogger =
        new DataLogger(
            String.format("../logs/%s.csv", uuid),
            List.of(
                "Time",
                "Generation",
                "Best Fitness",
                "Generation Size",
                "Chromosome Size",
                "Mutation Rate",
                "Crossover Rate",
                "Elitism",
                "Max Generations"));
    dataLogger.log(
        List.of(
            "n/a",
            "n/a",
            String.valueOf(generationSize),
            String.valueOf(chromosomeSize),
            String.valueOf(mutationRate),
            String.valueOf(crossoverRate),
            String.valueOf(elitism),
            String.valueOf(maxGenerations)));

    Generation generation = new Generation(generationSize, chromosomeSize);
    for (int i = 0; i < maxGenerations; i++) {
      generation =
          generation.getNextGeneration3(generationSize, mutationRate, crossoverRate, elitism);

      dataLogger.log(List.of(String.valueOf(i), String.valueOf(generation.getFittest().fitness())));
    }

    Chromosome best = generation.getFittest();
    Logger.getInstance()
        .log(
            String.join(
                ", ",
                uuid,
                String.valueOf(best.fitness()),
                String.valueOf(generationSize),
                String.valueOf(chromosomeSize),
                String.valueOf(mutationRate),
                String.valueOf(crossoverRate),
                String.valueOf(elitism),
                String.valueOf(maxGenerations),
                String.valueOf(best)));
  }

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
                "Crossover Rate",
                "Elitism",
                "Max Generations",
                "Best Route"));

    Thread thread1 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 2,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1));

    Thread thread2 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread3 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 600,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread4 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 300));

    Thread thread5 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread6 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.9,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread7 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.05,
                    /* crossoverRate= */ 0.9,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread8 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* crossoverRate= */ 0.9,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread9 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.05,
                    /* crossoverRate= */ 1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread10 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* crossoverRate= */ 1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 500));

    Thread thread11 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* crossoverRate= */ 1,
                    /* elitism= */ 10,
                    /* maxGenerations= */ 1000));

    Thread thread12 =
        new Thread(
            () ->
                tryRunEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* crossoverRate= */ 0.9,
                    /* elitism= */ 10,
                    /* maxGenerations= */ 1000));

    ExecutorService executor =
        Executors.newFixedThreadPool(
            (int) (Runtime.getRuntime().availableProcessors() * (3 / 4.0)));
    List<Thread> threads = List.of(thread10);

    for (Thread thread : threads) {
      executor.execute(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
    executor.shutdown();
  }
}

//  90394.47398522719
