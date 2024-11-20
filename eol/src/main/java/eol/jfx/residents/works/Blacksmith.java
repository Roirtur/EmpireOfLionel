package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import eol.jfx.ressources.TimeProductionForRessource;

public class Blacksmith extends Work {

  @Override
  public String toString() {
    return "Blacksmith";
  }

  @Override
  public void work() {
    isWorking = true;
    System.out.println("Working on steel...");
    // TODO Go take iron and coal from the quarry, and come back
    // TODO Take away ressources from the player inventory when taking them

    // When the work is done, generate income
    new Thread(() -> {
      try {
        Thread.sleep(TimeProductionForRessource.STEEL.getTime());
        generateRessources();
        System.out.println("Blacksmith finished working");
        isWorking = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void generateRessources() {
    PlayerInventory.productRessource(Ressource.STEEL, 1);
  }
}
