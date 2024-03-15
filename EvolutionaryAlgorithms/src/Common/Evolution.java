package Common;

import TravelingSalesman.Routes;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    DataLogger dataLogger =
        new DataLogger(
            String.format("../logs/%s.csv", uuid),
            List.of(
                "Time",
                "Generation",
                "Best Fitness",
                "Average Fitness",
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
            "n/a",
            String.valueOf(generationSize),
            String.valueOf(chromosomeSize),
            String.valueOf(mutationRate),
            String.valueOf(crossoverRate),
            String.valueOf(elitism),
            String.valueOf(maxGenerations)));

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
                uuid,
                String.valueOf(best.fitness()),
                String.valueOf(generationSize),
                String.valueOf(chromosomeSize),
                String.valueOf(mutationRate),
                selectionType.name(),
                crossoverType.name(),
                String.valueOf(crossoverRate),
                String.valueOf(elitism),
                String.valueOf(maxGenerations),
                String.valueOf(best)));
  }

  private Generation newGeneration(
      Config.ProblemType problemType, int generationSize, int chromosomeSize, Random random) {
    return switch (problemType) {
      case TravelingSalesman -> new Routes(generationSize, chromosomeSize, random);
      case EightQueensPuzzle -> null;
    };
  }
}
