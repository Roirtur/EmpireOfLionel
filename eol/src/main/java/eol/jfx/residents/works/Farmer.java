package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import eol.jfx.ressources.TimeProductionForRessource;

public class Farmer extends Work {

  @Override
  public String toString() {
    return "Farmer";
  }

  @Override
  public void work() {
    isWorking = true;
    System.out.println("Working on food...");
    // TODO Farm at the fields

    // When the work is done, generate income
    new Thread(() -> {
      try {
        Thread.sleep(TimeProductionForRessource.FOOD.getTime());
        generateRessources();
        System.out.println("Farmers finished working");
        isWorking = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void generateRessources() {
    PlayerInventory.productRessource(Ressource.FOOD, 1);
  }
}
