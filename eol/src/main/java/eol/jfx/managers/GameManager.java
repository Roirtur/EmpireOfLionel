package eol.jfx.managers;

import java.util.ArrayList;
import java.util.List;

import eol.jfx.buildings.Building;
import eol.jfx.buildings.BuildingFactory;
import eol.jfx.buildings.BuildingType;
import eol.jfx.residents.Resident;

public class GameManager {

    private final static List<Building> buildings = new ArrayList<>();
    private final List<Resident> residents = new ArrayList<>();

    // The single instance of the class
    private static volatile GameManager instance;

    // Private constructor to prevent instantiation
    private GameManager() {
    }

    // Public method to provide access to the single instance
    public static GameManager getInstance() {
        GameManager localInstance = instance;
        if (localInstance == null) {
            synchronized (GameManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GameManager();
                }
            }
        }
        return localInstance;
    }

    public static void addBuilding(BuildingType type, int x, int y) {
        Building building = BuildingFactory.createBuilding(type, x, y);

        if (!Map.placeBuilding(building, x, y)) {
            throw new IllegalStateException("Can't place building at " + x + ", " + y);
        }

        buildings.add(building);
    }

    public static void removeBuilding(int x, int y) {
        // Find the building at the given coordinates (checking size and position)
        Building building = null;
        for (Building b : buildings) {
            if (x >= b.getX() && x < b.getX() + b.getWidth() && y >= b.getY() && y < b.getY() + b.getHeight()) {
                building = b;
                break;
            }
        }
        if (building == null) {
            throw new IllegalStateException("No building found at " + x + ", " + y);
        }
        buildings.remove(building);
        Map.removeBuilding(building);
        building.remove();
    }

    public void addResident(Resident resident) {
        residents.add(resident);
    }

    public void removeResident(Resident resident) {
        residents.remove(resident);
    }

    public void assignWorkerToBuilding(Resident resident, Building building) {
        if (building.addWorkers(1) > 0) {
            throw new IllegalStateException("The building is full");
        }
        resident.setWorkplace(building);
    }

    public void updateResources() {
        // TODO
    }

    public void updateHunger() {
        for (Resident resident : residents) {
            resident.consumeFood();
        }
    }

    public void updateGame() {
        updateResources();
        updateHunger();
    }
}
