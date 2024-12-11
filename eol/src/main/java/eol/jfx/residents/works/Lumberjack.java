package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;
import java.util.Map;

public class Lumberjack extends Work {

  @Override
  public String toString() {
    return "Lumberjack";
  }

  @Override
  public WorkType getWorkType() {
    return WorkType.LUMBERJACK;
  }

  @Override
  public void generateRessources() {
    HashMap<Ressource, Integer> producedRessources =
        WorkType.LUMBERJACK.getProducedRessources();
    for (Map.Entry<Ressource, Integer> entry : producedRessources.entrySet()) {
      PlayerInventory.productRessource(entry.getKey(), entry.getValue());
    }

    HashMap<Ressource, Integer> consumedRessources =
        WorkType.LUMBERJACK.getConsumedRessources();
    for (Map.Entry<Ressource, Integer> entry : consumedRessources.entrySet()) {
      PlayerInventory.useRessource(entry.getKey(), entry.getValue());
    }
  }
}