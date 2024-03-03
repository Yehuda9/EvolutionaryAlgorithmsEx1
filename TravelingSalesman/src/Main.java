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
      int elitism,
      int maxGenerations) {
    String uuid = UUID.randomUUID().toString();
    DataLogger dataLogger = null;
    try {
      dataLogger =
          new DataLogger(
              String.format("../logs/%s.csv", uuid),
              List.of(
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
              "0",
              "0",
              String.valueOf(generationSize),
              String.valueOf(chromosomeSize),
              String.valueOf(mutationRate),
              String.valueOf(elitism),
              String.valueOf(maxGenerations)));
    }

    Generation generation = new Generation(generationSize, chromosomeSize);
    for (int i = 0; i < maxGenerations; i++) {
      double prevFittest = generation.getFittest().fitness();
      generation =
          generation.getNextGeneration(generationSize, chromosomeSize, mutationRate, elitism);
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
                String.valueOf(elitism),
                String.valueOf(maxGenerations),
                String.valueOf(best)));
  }

  public static void main(String[] args) throws InterruptedException {

    /*int generationSize = Integer.parseInt(args[0]);
    int chromosomeSize = Integer.parseInt(args[1]);
    double mutationRate = Double.parseDouble(args[2]);
    int elitism = Integer.parseInt(args[3]);
    int maxGenerations = Integer.parseInt(args[4]);*/

    Logger.getInstance()
        .log(
            String.join(
                ", ",
                "uuid",
                "Best Fitness",
                "Generation Size",
                "Chromosome Size",
                "Mutation Rate",
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
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread2 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread3 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread4 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread5 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.001,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread6 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 10000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread7 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 10000));

    Thread thread8 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 10000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 10000));

    Thread thread9 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 10000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.001,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 10000));

    Thread thread10 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 10000));

    Thread thread11 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.01,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100000));

    Thread thread12 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.05,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100000));

    ExecutorService executor =
        Executors.newFixedThreadPool(
            (int) (Runtime.getRuntime().availableProcessors() * (3 / 4.0)));
    List<Thread> threads =
        List.of(
            thread1, thread2, thread3, thread4, thread5, thread6, thread7, thread8, thread9,
            thread10, thread11, thread12);
    for (Thread thread : threads) {
      executor.execute(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
    executor.shutdown();
  }
}
