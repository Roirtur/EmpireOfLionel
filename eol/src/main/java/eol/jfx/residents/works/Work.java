package eol.jfx.residents.works;

public abstract class Work {

  public boolean isWorking = false;

  public void work() { System.out.println("Working..."); }

  public void generateRessources() { System.out.println("Generating..."); }
}