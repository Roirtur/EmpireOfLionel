package eol.jfx.buildings;

import eol.jfx.residents.works.*;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;

public class WoodenCabin extends Building {

  private final Work workertype = new Lumberjack();

  public WoodenCabin(int x, int y) {
    super(x, y, 1, 1, 2, 2, 2, new HashMap<Ressource, Integer>() {
      { put(Ressource.WOOD, 1); }
    });
  }

  @Override
  public Work getWorkerType() {
    return workertype;
  }
}
