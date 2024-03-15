package Common;

import EightQueenPuzzle.*;
import TravelingSalesman.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Evolution {
  private static final long SEED = 3447761810369037120L;
  public final Random random = new Random(SEED);

  public void tryRunEvolution(
      Config.ProblemType problemType,
      int generationSize,
      int chromosomeSize,
      double mutationRate,
      Config.SelectionType selectionType,
      Config.CrossoverType crossoverType,
      double crossoverRate,
      int elitism,
      int maxGenerations) {
    try {
      runEvolution(
          problemType,
          generationSize,
          chromosomeSize,
          mutationRate,
          selectionType,
          crossoverType,
          crossoverRate,
          elitism,
          maxGenerations);
    } catch (IOException e) {
      Logger.getInstance().log("Failed to run evolution: " + e.getMessage());
    }
  }

  public static void runParallel(Thread... threads) throws InterruptedException {
    ExecutorService executor =
        Executors.newFixedThreadPool(
            (int) (Runtime.getRuntime().availableProcessors() * (3 / 4.0)));

    for (Thread thread : threads) {
      executor.execute(thread);
    }
    for (Thread thread : threads) {
      thread.join();
    }
    executor.shutdown();
  }

  private void runEvolution(
      Config.ProblemType problemType,
      int generationSize,
      int chromosomeSize,
      double mutationRate,
      Config.SelectionType selectionType,
      Config.CrossoverType crossoverType,
      double crossoverRate,
      int elitism,
      int maxGenerations)
      throws IOException {
    String uuid = UUID.randomUUID().toString();
    System.out.printf("../logs/%s.csv,", uuid);

    DataLogger dataLogger = new DataLogger(String.format("../logs/%s.csv", uuid));

    Generation generation = newGeneration(problemType, generationSize, chromosomeSize, random);
    for (int i = 0; i < maxGenerations; i++) {
      generation =
          generation.getNextGeneration(
              generationSize, mutationRate, selectionType, crossoverType, crossoverRate, elitism);

      dataLogger.log(
          List.of(
              String.valueOf(i),
              String.valueOf(generation.getFittest().fitness()),
              String.valueOf(generation.getAverage())));
    }

    Chromosome best = generation.getFittest();
    Logger.getInstance()
        .log(
            String.join(
                ", ",
                "uuid",
                "Problem Type",
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
    Logger.getInstance()
        .log(
            String.join(
                ", ",
                uuid,
                problemType.name(),
                String.valueOf(best.fitness()),
                String.valueOf(generationSize),
                String.valueOf(chromosomeSize),
                String.valueOf(mutationRate),
                selectionType.name(),
                crossoverType.name(),
                String.valueOf(crossoverRate),
                String.valueOf(elitism),
                String.valueOf(maxGenerations),
                chromosomeToString(best, problemType)));
  }

  private Generation newGeneration(
      Config.ProblemType problemType, int generationSize, int chromosomeSize, Random random) {
    return switch (problemType) {
      case TravelingSalesman -> new Routes(generationSize, chromosomeSize, random);
      case EightQueensPuzzle -> new Puzzles(generationSize, chromosomeSize, random);
    };
  }

  private static String chromosomeToString(Chromosome chromosome, Config.ProblemType problemType) {
    switch (problemType) {
      case TravelingSalesman -> {
        Route route = (Route) chromosome;
        return route.toString();
      }
      case EightQueensPuzzle -> {
        Puzzle puzzle = (Puzzle) chromosome;
        return Arrays.toString(puzzle.getGenes());
      }
      default -> {
        return "";
      }
    }
  }
}
