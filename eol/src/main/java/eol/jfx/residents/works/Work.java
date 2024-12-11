package eol.jfx.residents.works;

import eol.jfx.managers.GameTime;
import eol.jfx.observers.TimeObserver;

public abstract class Work implements TimeObserver {

  public boolean isWorking = false;
  private int startHour = -1;

  public Work() { GameTime.getInstance().addObserver(this); }

  public void work() {
    if (!isWorking) {
      isWorking = true;
      startHour = (int)GameTime.getInstance().getCurrentHour();
      System.out.println("Started working...");
    }
  }

  @Override
  public void update(int currentHour) {
    if (isWorking && startHour != -1) {
      int productionTime = getWorkType().getProductionTime();
      if (currentHour - startHour >= productionTime) {
        generateRessources();
        isWorking = false;
        startHour = -1;
        System.out.println("Finished working...");
      }
    }
  }

  public abstract WorkType getWorkType();

  public void goToWorkplace() { System.out.println("Going to workplace..."); }

  public void goToSleep() { System.out.println("Going to sleep..."); }

  public void generateRessources() { System.out.println("Generating..."); }
}