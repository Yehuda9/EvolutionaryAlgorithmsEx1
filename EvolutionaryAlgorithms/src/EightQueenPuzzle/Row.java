package EightQueenPuzzle;

import Common.Gene;

public record Row(int index) implements Gene {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Row row = (Row) o;
    return index == row.index;
  }
}
