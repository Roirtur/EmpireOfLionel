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
    // TODO Farm at the fields

    // When the work is done, generate income
    new Thread(() -> {
      try {
        Thread.sleep(TimeProductionForRessource.FOOD.getTime());
        generateRessources();
        System.out.println("Farmer has finished working");
        isWorking = false;
      } catch (InterruptedException e) {
        System.err.println("Thread was interrupted!");
      }
    }).start();
  }

  @Override
  public void generateRessources() {
    PlayerInventory.productRessource(Ressource.FOOD, 1000);
  }
}
