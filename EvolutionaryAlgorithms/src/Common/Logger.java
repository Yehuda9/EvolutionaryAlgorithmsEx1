package Common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.ZoneId;

public class Logger {

  private static final String LOG_FILE = "../logs/log.csv";
  private static Logger instance;
  private final OutputStreamWriter writer;

  private Logger() {
    OutputStreamWriter tempWriter;
    try {
      tempWriter = new FileWriter(LOG_FILE, /* append= */ true);
    } catch (IOException e) {
      tempWriter = new OutputStreamWriter(System.out);
    }
    writer = tempWriter;
  }

  public static Logger getInstance() {
    if (instance == null) {
      instance = new Logger();
    }
    return instance;
  }

  public void log(String message) {
    new Thread(
            () -> {
              try {
                writer.write(
                    Instant.now().atZone(ZoneId.systemDefault()).toLocalTime()
                        + ", "
                        + message
                        + "\n");
                writer.flush();
              } catch (Exception e) {
                System.out.println("logging failed " + e.getMessage());
              }
            })
        .start();
  }
}
