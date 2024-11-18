package eol.jfx.buildings;

import eol.jfx.residents.works.*;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;

public class House extends Building {

  private final Work workertype = null;

  public House(int x, int y) {
    super(x, y, 2, 2, 4, 0, 4, new HashMap<Ressource, Integer>() {
      {
        put(Ressource.WOOD, 2);
        put(Ressource.STONE, 2);
      }
    });
  }

  @Override
  public Work getWorkerType() {
    return workertype;
  }
}
