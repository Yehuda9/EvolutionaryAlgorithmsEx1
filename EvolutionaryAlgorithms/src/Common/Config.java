package Common;

public class Config {

  public enum ProblemType {
    TravelingSalesman,
    EightQueensPuzzle
  }

  public enum CrossoverType {
    SinglePoint,
    TwoPoints
  }

  public enum SelectionType {
    RouletteWheel,
    Rank,
    Tournament
  }
}
