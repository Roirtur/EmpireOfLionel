package eol.jfx.ressources;

public enum TimeProductionForRessource {
  FOOD(1000),
  WOOD(1000),
  STONE(1000),
  COAL(1000),
  IRON(1000),
  STEEL(1000),
  CEMENT(1000),
  LUMBER(1000),
  TOOLS(1000);

  private final int time;

  TimeProductionForRessource(int time) { this.time = time; }

  public int getTime() { return time; }
}
