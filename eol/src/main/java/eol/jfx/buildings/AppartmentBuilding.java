package eol.jfx.buildings;

import eol.jfx.residents.works.*;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;

public class AppartmentBuilding extends Building {

  private final Work workertype = null;

  public AppartmentBuilding(int x, int y) {
    super(x, y, 3, 2, 60, 0, 6, new HashMap<Ressource, Integer>() {
      {
        put(Ressource.WOOD, 50);
        put(Ressource.STONE, 50);
      }
    });
  }

  @Override
  public Work getWorkerType() {
    return workertype;
  }
}
