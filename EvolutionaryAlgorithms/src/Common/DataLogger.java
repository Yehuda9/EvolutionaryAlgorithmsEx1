package Common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class DataLogger {
  private final OutputStreamWriter writer;

  public DataLogger(String path, List<String> attributes) throws IOException {
    OutputStreamWriter tempWriter;
    try {
      tempWriter = new FileWriter(path, /* append= */ true);
    } catch (IOException e) {
      tempWriter = new OutputStreamWriter(System.out);
    }
    writer = tempWriter;
    writer.write(String.join(", ", attributes) + "\n");
  }

  public DataLogger(String path) throws IOException {
    this(path, List.of("Time", "Generation", "Best Fitness", "Average Fitness"));
  }

  public void log(List<String> data) {
    new Thread(
            () -> {
              try {
                writer.write(
                    Instant.now().atZone(ZoneId.systemDefault()).toLocalTime()
                        + ", "
                        + String.join(", ", data)
                        + "\n");
                writer.flush();
              } catch (Exception e) {
                System.out.println("logging failed " + e.getMessage());
              }
            })
        .start();
  }
}
