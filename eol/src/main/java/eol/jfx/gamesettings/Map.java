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

  public boolean canPlaceBuilding(Building building, int x, int y) {

    int buildingWidth = building.getWidth();
    int buildingHeight = building.getHeight();

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

    if (!canPlaceBuilding(building, x, y)) {
      throw new IllegalArgumentException(
          "Cannot place building at this position");
    }

    int buildingWidth = building.getWidth();
    int buildingHeight = building.getHeight();

    for (int i = 0; i < buildingHeight; i++) {
      for (int j = 0; j < buildingWidth; j++) {
        grid[y + i][x + j] = true;
      }
    }
  }

  public void removeBuilding(Building building) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (grid[i][j]) {
          grid[i][j] = false;
        }
      }
    }
  }
}
