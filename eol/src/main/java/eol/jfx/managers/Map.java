package eol.jfx.managers;

import eol.jfx.buildings.Building;

public class Map {

  private final static int WIDTH = 34;
  private final static int HEIGHT = 18;
  private final static boolean[][] grid = new boolean[HEIGHT][WIDTH];

  // The single instance of the class
  private static volatile Map instance;

  // Private constructor to prevent instantiation
  private Map() {}

  // Public method to provide access to the single instance
  public static Map getInstance() {
    Map localInstance = Map.instance;
    if (localInstance == null) {
      // Safely create the instance if it doesn't exist (double-checked locking)
      synchronized (Map.class) {
        if (localInstance == null) {
          Map.instance = instance = new Map();
        }
      }
    }
    return localInstance;
  }

  public static boolean[][] getGrid() {
    return grid;
  }

  public static boolean canPlaceBuilding(int buildingWidth, int buildingHeight,
                                         int x, int y) {
    // TODO: Maybe return position of the occupied cells
    if (x < 0 || y < 0 || x + buildingWidth > WIDTH ||
        y + buildingHeight > HEIGHT) {
      return false;
    }

    System.out.println("Checking if building can be placed at " + x + ", " + y +
                       " with width " + buildingWidth + " and height " +
                       buildingHeight);

    for (int i = x; i < x + buildingWidth; i++) {
      for (int j = y; j < y + buildingHeight; j++) {
        System.out.println("Checking grid at " + i + ", " + j + " = " +
                           grid[i][j]);
        if (grid[j][i]) {
          return false;
        }
      }
    }
    return true;
  }

  public static boolean placeBuilding(Building building, int x, int y) {

    int buildingWidth = building.getWidth();
    int buildingHeight = building.getHeight();

    if (!canPlaceBuilding(buildingWidth, buildingHeight, x, y)) {
      System.out.println("Can't place building at " + x + ", " + y);
      return false;
    }

    for (int i = x; i < x + buildingWidth; i++) {
      for (int j = y; j < y + buildingHeight; j++) {
        grid[j][i] = true;
      }
    }

    return true;
  }

  public static void removeBuilding(Building building) {
    int x = building.getX();
    int y = building.getY();
    int buildingWidth = building.getWidth();
    int buildingHeight = building.getHeight();

    for (int i = x; i < x + buildingWidth; i++) {
      for (int j = y; j < y + buildingHeight; j++) {
        grid[j][i] = false;
      }
    }
  }

  public static void printGrid() {
    System.out.println("--- Grid ---");
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        System.out.print(grid[i][j] ? "X " : "O ");
      }
      System.out.println();
    }
    System.out.println("------------");
  }

  public static int getWidth() { return WIDTH; }

  public static int getHeight() { return HEIGHT; }
}
