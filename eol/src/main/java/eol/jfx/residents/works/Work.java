package eol.jfx.residents.works;

public abstract class Work {

  public boolean isWorking = false;

  public void work() { System.out.println("Working..."); }

  public void goToWorkplace() { System.out.println("Going to workplace..."); }

  public void goToSleep() { System.out.println("Going to sleep..."); }

  public void generateRessources() { System.out.println("Generating..."); }
}