package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;
import java.util.Map;

public class CementProductor extends Work {

  @Override
  public String toString() {
    return "CementProductor";
  }

  @Override
  public WorkType getWorkType() {
    return WorkType.CEMENTPRODUCTOR;
  }

  @Override
  public void generateRessources() {
    HashMap<Ressource, Integer> producedRessources =
        WorkType.CEMENTPRODUCTOR.getProducedRessources();
    for (Map.Entry<Ressource, Integer> entry : producedRessources.entrySet()) {
      PlayerInventory.productRessource(entry.getKey(), entry.getValue());
    }

    HashMap<Ressource, Integer> consumedRessources =
        WorkType.CEMENTPRODUCTOR.getConsumedRessources();
    for (Map.Entry<Ressource, Integer> entry : consumedRessources.entrySet()) {
      PlayerInventory.useRessource(entry.getKey(), entry.getValue());
    }
  }
}