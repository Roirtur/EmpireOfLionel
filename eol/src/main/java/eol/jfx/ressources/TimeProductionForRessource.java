package eol.jfx.ressources;

public enum TimeProductionForRessource {
  FOOD(5),
  WOOD(15),
  STONE(25),
  COAL(50),
  IRON(100),
  STEEL(150),
  CEMENT(200),
  LUMBER(250),
  TOOLS(300);

  private final int time;

  TimeProductionForRessource(int time) { this.time = time; }

  public int getTime() { return time; }
}
