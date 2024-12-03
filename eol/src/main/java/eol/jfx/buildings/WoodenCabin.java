package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.Ressource;

public class WoodenCabin extends Building {

  private final WorkType workertype = WorkType.LUMBERJACK;

  public final static BuildingType TYPE = BuildingType.WOODENCABIN;

  public WoodenCabin(int x, int y) {
    super(x, y, BuildingType.WOODENCABIN.getWidth(), BuildingType.WOODENCABIN.getHeight(), 2, 2, 2, new HashMap<Ressource, Integer>() {
      { put(Ressource.WOOD, 1); }
    });
  }

  @Override
  public WorkType getWorkerType() {
    return workertype;
  }
}
