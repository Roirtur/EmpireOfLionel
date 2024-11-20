package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import eol.jfx.ressources.TimeProductionForRessource;

public class Miner extends Work {

  @Override
  public String toString() {
    return "Miner";
  }

  @Override
  public void work() {
    isWorking = true;
    System.out.println("Mining resources...");
    // TODO Mine at the Quarry

    // When the work is done, generate income
    new Thread(() -> {
      try {
        Ressource[] ressources = {Ressource.STONE, Ressource.COAL,
                                  Ressource.IRON};
        Ressource selectedRessource =
            ressources[new java.util.Random().nextInt(ressources.length)];
        long productionTime =
            TimeProductionForRessource.valueOf(selectedRessource.name())
                .getTime();
        Thread.sleep(productionTime);
        PlayerInventory.productRessource(selectedRessource, 1);
        System.out.println("Miner finished working on " +
                           selectedRessource.name().toLowerCase());
        isWorking = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void generateRessources() {
    // Chose randomly between these, with odds
    PlayerInventory.productRessource(Ressource.STONE, 1);
    PlayerInventory.productRessource(Ressource.COAL, 1);
    PlayerInventory.productRessource(Ressource.IRON, 1);
  }
}
