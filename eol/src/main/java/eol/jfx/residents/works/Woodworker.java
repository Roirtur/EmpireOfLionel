package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;
import java.util.Map;

public class Woodworker extends Work {

  @Override
  public String toString() {
    return "Woodworker";
  }

  @Override
  public WorkType getWorkType() {
    return WorkType.WOODWORKER;
  }

  @Override
  public void generateRessources() {
    HashMap<Ressource, Integer> producedRessources =
        WorkType.WOODWORKER.getProducedRessources();
    for (Map.Entry<Ressource, Integer> entry : producedRessources.entrySet()) {
      PlayerInventory.productRessource(entry.getKey(), entry.getValue());
    }

    HashMap<Ressource, Integer> consumedRessources =
        WorkType.WOODWORKER.getConsumedRessources();
    for (Map.Entry<Ressource, Integer> entry : consumedRessources.entrySet()) {
      PlayerInventory.useRessource(entry.getKey(), entry.getValue());
    }
  }
}