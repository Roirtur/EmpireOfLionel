package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import eol.jfx.ressources.TimeProductionForRessource;

public class Miner extends Work {

  private Ressource currentRessource;

  @Override
  public String toString() {
    return "Miner";
  }

  @Override
  public void work() {
    isWorking = true;
    System.out.println("Mining resources...");

    new Thread(() -> {
      try {
        generateRessources();
        Ressource selectedRessource = currentRessource;

        long productionTime =
            TimeProductionForRessource.valueOf(selectedRessource.name())
                .getTime();

        Thread.sleep(productionTime);

        System.out.println("Miner finished working on " +
                           selectedRessource.name().toLowerCase());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        isWorking = false;
      }
    }).start();
  }

  @Override
  public void generateRessources() {
    double random = Math.random();
    if (random < 0.6) {
      currentRessource = Ressource.STONE;
    } else if (random < 0.9) {
      currentRessource = Ressource.COAL;
    } else {
      currentRessource = Ressource.IRON;
    }

    PlayerInventory.productRessource(currentRessource, 1);
    System.out.println("Generated " + currentRessource.name().toLowerCase());
  }
}
