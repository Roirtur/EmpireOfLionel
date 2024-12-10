package eol.jfx.buildings;
import java.util.HashMap;
import java.util.Map;

public enum BuildingType {
  APARTMENT(4, 4),
  CEMENTPLANT(3, 3),
  FARM(2, 2),
  HOUSE(1, 1),
  LUMBERMILL(3, 3),
  QUARY(3, 3),
  STEELMILL(3, 3),
  TOOLFACTORY(2, 2),
  WOODENCABIN(1, 1);

  private final int width;
  private final int height;

  BuildingType(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getWidth() { return width; }

  public int getHeight() { return height; }

  public Map<String, Integer> getConstructionMap() {
    Map<String, Integer> costMap = new HashMap<>();
    switch (this) {
    case APARTMENT:
      costMap.put("WOOD", 50);
      costMap.put("STONE", 50);
      break;
    case CEMENTPLANT:
      costMap.put("WOOD", 50);
      costMap.put("STONE", 50);
      break;
    case FARM:
      costMap.put("WOOD", 5);
      costMap.put("STONE", 5);
      break;
    case HOUSE:
      costMap.put("WOOD", 2);
      costMap.put("STONE", 2);
      break;
    case LUMBERMILL:
      costMap.put("WOOD", 50);
      costMap.put("STONE", 50);
      break;
    case QUARY:
      costMap.put("WOOD", 50);
      break;
    case STEELMILL:
      costMap.put("WOOD", 100);
      costMap.put("STONE", 50);
      break;
    case TOOLFACTORY:
      costMap.put("WOOD", 50);
      costMap.put("STONE", 50);
      break;
    case WOODENCABIN:
      costMap.put("WOOD", 1);
      break;
    }
    return costMap;
  }
}
