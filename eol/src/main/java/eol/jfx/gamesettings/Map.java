package eol.jfx.gamesettings;
import eol.jfx.buildings.Building;

public class Map {

  private final int width;
  private final int height;

  private final boolean[][] grid;

  public Map(int width, int height) {
    this.width = width;
    this.height = height;
    this.grid = new boolean[height][width];
  }

  public boolean canPlaceBuilding(int buildingWidth, int buildingHeight, int x, int y) {

    if (x < 0 || y < 0 || x + buildingWidth > width ||
        y + buildingHeight > height) {
      return false;
    }

    for (int i = x; i < x + buildingWidth; i++) {
      for (int j = y; j < y + buildingHeight; j++) {
        if (grid[j][i]) {
          return false;
        }
      }
    }

    return true;
  }

  public void placeBuilding(Building building, int x, int y) {

    int buildingWidth = building.getWidth();
    int buildingHeight = building.getHeight();

    if (!canPlaceBuilding(buildingWidth, buildingHeight, x, y)) {
      throw new IllegalArgumentException(
          "Cannot place building at this position");
    }

    for (int i = x; i < x + buildingWidth; i++) {
      for (int j = y; j < y + buildingHeight; j++) {
        grid[i][j] = true;
      }
    }
  }

  public void removeBuilding(Building building) {
      int x = building.getX();
      int y = building.getY();
      int buildingWidth = building.getWidth();
      int buildingHeight = building.getHeight();
  
      for (int i = x; i < x + buildingWidth; i++) {
        for (int j = y; j < y + buildingHeight; j++) {
          grid[i][j] = false;
        }
      }
  }
}
