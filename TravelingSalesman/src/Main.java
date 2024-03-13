import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void runEvolution(
      int generationSize,
      int chromosomeSize,
      double mutationRate,
      double crossoverRate,
      int elitism,
      int maxGenerations) {
    String uuid = UUID.randomUUID().toString();
    DataLogger dataLogger = null;
    try {
      dataLogger =
          new DataLogger(
              String.format("../logs/%s.csv", uuid),
              List.of(
                  "Time",
                  "Generation",
                  "Best Fitness",
                  "Generation Size",
                  "Chromosome Size",
                  "Mutation Rate",
                  "Elitism",
                  "Max Generations"));
    } catch (IOException e) {
      System.out.println("Failed to create data logger " + e.getMessage());
    }
    if (dataLogger != null) {
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
    }

    Generation generation = new Generation(generationSize, chromosomeSize);
    for (int i = 0; i < maxGenerations; i++) {
      double prevFittest = generation.getFittest().fitness();
      generation =
          generation.getNextGeneration2(generationSize, mutationRate, crossoverRate, elitism);
      if (dataLogger != null) {
        dataLogger.log(
            List.of(String.valueOf(i), String.valueOf(generation.getFittest().fitness())));
      }

      if (generation.getFittest().fitness() > prevFittest) {
        System.out.println("***Previous fittest is better than current fittest***");
      }
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
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread2 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread3 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 600,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread4 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 300));

    Thread thread5 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.5,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread6 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* crossoverRate= */ 0.9,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread7 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.05,
                    /* crossoverRate= */ 0.9,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread8 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 300,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* crossoverRate= */ 0.9,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread9 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.05,
                    /* crossoverRate= */ 1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread10 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* crossoverRate= */ 1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread11 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* crossoverRate= */ 1,
                    /* elitism= */ 10,
                    /* maxGenerations= */ 1000));

    Thread thread12 =
        new Thread(
            () ->
                runEvolution(
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
