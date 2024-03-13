public class TestsUtils {
  public static void assertCondition(boolean condition) {
    if (!condition) {
      throw new RuntimeException("Test failed");
    }
  }
}
