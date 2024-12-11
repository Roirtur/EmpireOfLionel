package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;
import java.util.Map;

public class Farmer extends Work {

  @Override
  public String toString() {
    return "Farmer";
  }

  @Override
  public WorkType getWorkType() {
    return WorkType.FARMER;
  }

  @Override
  public void generateRessources() {
    HashMap<Ressource, Integer> producedRessources =
        WorkType.FARMER.getProducedRessources();
    for (Map.Entry<Ressource, Integer> entry : producedRessources.entrySet()) {
      PlayerInventory.productRessource(entry.getKey(), entry.getValue());
    }

    HashMap<Ressource, Integer> consumedRessources =
        WorkType.FARMER.getConsumedRessources();
    for (Map.Entry<Ressource, Integer> entry : consumedRessources.entrySet()) {
      PlayerInventory.useRessource(entry.getKey(), entry.getValue());
    }
  }
}