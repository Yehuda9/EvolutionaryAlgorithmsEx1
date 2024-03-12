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
              String.valueOf(elitism),
              String.valueOf(maxGenerations)));
    }

    Generation generation = new Generation(generationSize, chromosomeSize);
    for (int i = 0; i < maxGenerations; i++) {
      double prevFittest = generation.getFittest().fitness();
      generation = generation.getNextGeneration1(generationSize, mutationRate, elitism);
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

    Thread thread13 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 2000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 10,
                    /* maxGenerations= */ 1000));

    Thread thread14 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 2000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 10,
                    /* maxGenerations= */ 2000));

    Thread thread15 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 2000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 20,
                    /* maxGenerations= */ 2000));

    Thread thread16 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread17 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread18 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread19 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread20 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread21 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 2000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread22 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 2000));

    Thread thread23 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 2000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 2000));

    Thread thread24 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 2000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.4,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 2000));

    Thread thread25 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread26 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 500,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread27 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 500));

    Thread thread28 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.2,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread29 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.3,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 100));

    Thread thread30 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 1000));

    Thread thread31 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 1000,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 500));

    Thread thread32 =
        new Thread(
            () ->
                runEvolution(
                    /* generationSize= */ 100,
                    /* chromosomeSize= */ 48,
                    /* mutationRate= */ 0.1,
                    /* elitism= */ 2,
                    /* maxGenerations= */ 10000));

    ExecutorService executor =
        Executors.newFixedThreadPool(
            (int) (Runtime.getRuntime().availableProcessors() * (3 / 4.0)));
    List<Thread> threads =
        List.of(/*thread1, thread2, thread3, thread4, thread5, thread6,  thread7,
            thread8,
            thread9,
            thread10, thread11, thread12, thread13, thread14, thread15 thread16,
            thread17,
            thread18,
            thread19, thread20, thread21, thread22, thread23, thread24 thread25,
            thread26,
            thread27,
            thread28,
            thread29 thread27, thread30,*/ thread32);
    for (Thread thread : threads) {
      executor.execute(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
    executor.shutdown();
  }
}
