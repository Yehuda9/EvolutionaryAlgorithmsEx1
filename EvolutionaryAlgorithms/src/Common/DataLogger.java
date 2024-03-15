package Common;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class DataLogger {
  private final FileWriter writer;

  public DataLogger(String path, List<String> attributes) throws IOException {
    writer = new FileWriter(path, /* append= */ true);
    writer.write(String.join(", ", attributes) + "\n");
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
