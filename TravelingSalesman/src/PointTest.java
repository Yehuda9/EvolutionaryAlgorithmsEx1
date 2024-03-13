public class PointTest {
  private Point point;
  private Point other;

  void distanceTo_isPositive() {
    point = new Point(0, 0);
    other = new Point(3, 4);

    TestsUtils.assertCondition(point.distanceTo(other) > 0);
  }

  void distanceTo_calculatedRight() {
    point = new Point(0, 0);
    other = new Point(3, 4);

    TestsUtils.assertCondition(point.distanceTo(other) == 5);
  }

  void distanceTo_isSymmetric() {
    point = new Point(0, 0);
    other = new Point(3, 4);

    TestsUtils.assertCondition(point.distanceTo(other) == other.distanceTo(point));
  }

  void distanceTo_isReflexive() {
    point = new Point(3, 4);

    TestsUtils.assertCondition(point.distanceTo(point) == 0);
  }

  void distanceTo_triangleInequality() {
    point = new Point(0, 0);
    other = new Point(3, 4);
    Point another = new Point(6, 8);

    TestsUtils.assertCondition(
        point.distanceTo(another) <= point.distanceTo(other) + other.distanceTo(another));
  }

  public static void main(String[] args) {
    PointTest test = new PointTest();
    test.distanceTo_calculatedRight();
    test.distanceTo_isPositive();
    test.distanceTo_isSymmetric();
    test.distanceTo_isReflexive();
    test.distanceTo_triangleInequality();
  }
}
