public class Main {
  private static final int MAX_FITNESS = 28;

  public static Chromosome runEvolution(
      int generationSize, double mutationRate, int maxGenerations) {
    Generation generation = new Generation(generationSize);
    for (int i = 0; i < maxGenerations; i++) {
      if (generation.getFittest().fitness() == MAX_FITNESS) {
        System.out.println("Solution found in generation " + i);
        break;
      }

      generation = generation.getNextGeneration(generationSize, mutationRate);
    }

    return generation.getFittest();
  }

  public static void main(String[] args) {

    int generationSize = Integer.parseInt(args[0]);
    double mutationRate = Double.parseDouble(args[1]);
    int maxGenerations = Integer.parseInt(args[2]);

    Chromosome best = runEvolution(generationSize, mutationRate, maxGenerations);

    System.out.println(best);
  }
}
