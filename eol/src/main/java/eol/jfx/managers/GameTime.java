package eol.jfx.managers;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The GameTime class is a singleton that manages the in-game time. It uses a
 * Timer to increment the time every second. The time is represented in seconds
 * and can be formatted to display days and hours. The class also keeps track of
 * day and night cycles.
 */
public class GameTime {

  private static volatile GameTime instance;
  private final Timer timer;
  private int seconds;

  private final int NIGHT_TIME = 19;
  private final int DAY_TIME = 7;
  private boolean night = false;

  private long days;
  private long hours;

  private final StringProperty timeProperty = new SimpleStringProperty();

  // Private constructor to prevent instantiation
  private GameTime() {
    timer = new Timer();
    seconds = 0;
  }

  // Public method to provide access to the single instance
  public static GameTime getInstance() {
    GameTime localInstance = instance;
    if (localInstance == null) {
      synchronized (GameTime.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new GameTime();
        }
      }
    }
    return localInstance;
  }

  public void startTimer() {
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        incrementTime();
      }
    }, 500, 500);
  }

  public void stopTimer() { timer.cancel(); }

  private synchronized void incrementTime() {
    seconds++;
    GameManager.getInstance().updateTime();
    checkNight();
    updateTimeProperty();
  }

  private void updateTimeProperty() {
    Platform.runLater(() -> { timeProperty.set(displayTime()); });
  }

  public StringProperty timeProperty() { return timeProperty; }

  public String displayTime() { return formatTime(seconds); }

  public int getSeconds() { return seconds; }

  private String formatTime(long time) {
    days = Math.floorDiv(time, 24) + 1;
    hours = time % 24;
    return String.format("Day %d, %02d:00", days, hours);
  }

  private void checkNight() {
    if (hours >= NIGHT_TIME || hours < DAY_TIME) {
      if (!night) {
        changeNightState();
      }
    } else {
      if (night) {
        changeNightState();
      }
    }
  }

  private void changeNightState() {
    night = !night;
    GameManager.updateNight();
  }

  public static boolean isNight() { return getInstance().night; }
}