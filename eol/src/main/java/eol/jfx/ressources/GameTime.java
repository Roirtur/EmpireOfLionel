package eol.jfx.ressources;

public class GameTime {

  private static volatile GameTime instance;
  private long timeInSeconds = 0;
  private boolean running = true;

  private GameTime() { startTimer(); }

  public static GameTime getInstance() {
    if (instance == null) {
      synchronized (GameTime.class) {
        if (instance == null) {
          instance = new GameTime();
        }
      }
    }
    return instance;
  }

  private void startTimer() {
    Thread timerThread = new Thread(() -> {
      while (running) {
        try {
          Thread.sleep(1000);
          incrementTime();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    timerThread.setDaemon(true);
    timerThread.start();
  }

  private synchronized void incrementTime() {
    timeInSeconds++;
    System.out.println("Game time: " + timeInSeconds + " seconds");
  }

  public void displayGameTime() {
    long time = getInstance().getTimeInSeconds();
    System.out.println("Time: " + formatTime(time));
  }

  private String formatTime(long time) {
    long hours = (time / 3600) % 24;
    long minutes = (time % 3600) / 60;
    long seconds = time % 60;
    return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
  }

  public synchronized long getTimeInSeconds() { return timeInSeconds; }

  public synchronized void stopTimer() { running = false; }
}
