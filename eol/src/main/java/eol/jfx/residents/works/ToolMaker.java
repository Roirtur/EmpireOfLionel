package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import eol.jfx.ressources.TimeProductionForRessource;

public class ToolMaker extends Work {
  @Override
  public String toString() {
    return "ToolMaker";
  }

  @Override
  public void work() {
    isWorking = true;
    System.out.println("Working on tools...");
    // TODO Go take steel from the SteelMill, and coal from the CoalMine, and
    // come back
    // TODO Take away ressources from the player inventory when taking them

    // When the work is done, generate income
    new Thread(() -> {
      try {
        Thread.sleep(TimeProductionForRessource.TOOLS.getTime());
        generateRessources();
        System.out.println("ToolMaker finished working");
        isWorking = false;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void generateRessources() {
    PlayerInventory.productRessource(Ressource.TOOLS, 1);
  }
}
