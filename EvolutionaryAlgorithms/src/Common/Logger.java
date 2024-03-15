package Common;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;

public class Logger {

  private static final String LOG_FILE = "../logs/log.csv";
  private static Logger instance;
  private FileWriter writer;

  private Logger() {
    try {
      writer = new FileWriter(LOG_FILE, /* append= */ true);
    } catch (IOException e) {
      System.out.println("Failed to create log file " + e.getMessage());
    }
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
