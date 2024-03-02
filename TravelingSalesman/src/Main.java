import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void runEvolution(
      int generationSize,
      int chromosomeSize,
      double mutationRate,
      int elitism,
      int maxGenerations) {
    Generation generation = new Generation(generationSize, chromosomeSize);
    for (int i = 0; i < maxGenerations; i++) {
      double prevFittest = generation.getFittest().fitness();
      generation =
          generation.getNextGeneration(generationSize, chromosomeSize, mutationRate, elitism);

      if (generation.getFittest().fitness() > prevFittest) {
        System.out.println(
            "Generation: "
                + i
                + " Fittest: "
                + generation.getFittest().fitness()
                + " Previous Fittest: "
                + prevFittest);
      }
    }

    Chromosome best = generation.getFittest();
    System.out.println(best.fitness());
    saveBest(best, generationSize, chromosomeSize, mutationRate, elitism, maxGenerations);
  }

  private static void saveBest(
      Chromosome best,
      int generationSize,
      int chromosomeSize,
      double mutationRate,
      int elitism,
      int maxGenerations) {
    List<String> routeData = new ArrayList<>();
    boolean writeToFile = true;
    try (BufferedReader reader =
        new BufferedReader(new FileReader("TravelingSalesman/data/route.txt"))) {
      routeData = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        routeData.add(line);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    if (routeData.size() >= 2) {
      writeToFile = Double.parseDouble(routeData.get(1)) > best.fitness();
    }

    if (writeToFile) {
      try (BufferedWriter writer =
          new BufferedWriter(new FileWriter("TravelingSalesman/data/route.txt"))) {
        writer.write(String.valueOf(System.currentTimeMillis()));
        writer.newLine();
        writer.write(String.valueOf(best.fitness()));
        writer.newLine();
        writer.write(best.toString());
        writer.newLine();
        writer.write(
            "Generation Size: "
                + generationSize
                + " Chromosome Size: "
                + chromosomeSize
                + " Mutation Rate: "
                + mutationRate
                + " Elitism: "
                + elitism
                + " Max Generations: "
                + maxGenerations);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void main(String[] args) {

    /*int generationSize = Integer.parseInt(args[0]);
    int chromosomeSize = Integer.parseInt(args[1]);
    double mutationRate = Double.parseDouble(args[2]);
    int elitism = Integer.parseInt(args[3]);
    int maxGenerations = Integer.parseInt(args[4]);*/

    runEvolution(
        /* generationSize= */ 10000,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.01,
        /* elitism= */ 2,
        /* maxGenerations= */ 1000);

    runEvolution(
        /* generationSize= */ 1000,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.01,
        /* elitism= */ 2,
        /* maxGenerations= */ 10000);

    runEvolution(
        /* generationSize= */ 10000,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.01,
        /* elitism= */ 2,
        /* maxGenerations= */ 10000);

    System.exit(0);

    runEvolution(
        /* generationSize= */ 100,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.01,
        /* elitism= */ 2,
        /* maxGenerations= */ 100);

    runEvolution(
        /* generationSize= */ 1000,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.01,
        /* elitism= */ 2,
        /* maxGenerations= */ 100);

    runEvolution(
        /* generationSize= */ 100,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.01,
        /* elitism= */ 2,
        /* maxGenerations= */ 1000);

    runEvolution(
        /* generationSize= */ 1000,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.01,
        /* elitism= */ 2,
        /* maxGenerations= */ 1000);

    runEvolution(
        /* generationSize= */ 1000,
        /* chromosomeSize= */ 48,
        /* mutationRate= */ 0.001,
        /* elitism= */ 2,
        /* maxGenerations= */ 1000);
  }
}
