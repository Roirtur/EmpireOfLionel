package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import eol.jfx.ressources.TimeProductionForRessource;

public class Lumberjack extends Work {

  @Override
  public String toString() {
    return "Lumberjack";
  }

  @Override
  public void work() {
    isWorking = true;
    System.out.println("Working on wood...");
    // TODO Go take wood from the woodencabin, and come back
    // TODO Take away ressources from the player inventory when taking them

    // When the work is done, generate income
    new Thread(() -> {
      try {
        Thread.sleep(TimeProductionForRessource.WOOD.getTime());
        generateRessources();
        System.out.println("Woodworkers finished working");
        isWorking = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void generateRessources() {
    PlayerInventory.productRessource(Ressource.WOOD, 1);
  }
}
