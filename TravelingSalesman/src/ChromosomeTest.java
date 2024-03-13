public class ChromosomeTest {

  private static final int chromosomeSize = 48;
  private Chromosome chromosome;

  void setup() {
    Data.init("TravelingSalesman/data/tsp.txt");
  }

  void fitness_isPositive() {
    setup();
    chromosome = new Chromosome(chromosomeSize);

    TestsUtils.assertCondition(chromosome.fitness() > 0);
  }

  void fitness_calculatedRight() {
    setup();

    chromosome = new Chromosome(new Point[] {new Point(0, 0), new Point(3, 4)});

    TestsUtils.assertCondition(chromosome.fitness() == 10);
  }

  public static void main(String[] args) {
    ChromosomeTest test = new ChromosomeTest();
    test.fitness_calculatedRight();
    test.fitness_isPositive();
  }
}
