package eol.jfx.managers;

import java.util.ArrayList;
import java.util.List;

import eol.jfx.buildings.Building;
import eol.jfx.buildings.BuildingFactory;
import eol.jfx.buildings.BuildingType;
import eol.jfx.residents.Resident;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class GameManager {

    private final List<Building> buildings = new ArrayList<>();
    private final List<Resident> residents = new ArrayList<>();
    private final List<Resident> unemployed_residents = new ArrayList<>();
    private final List<Resident> working_residents = new ArrayList<>();

    public boolean night_updated = false;
    public boolean time_updated = false;

    private static boolean running = true;

    private final Thread gameThread = new Thread(this::lauchGame);

    // The single instance of the class
    private static volatile GameManager instance;

    // Private constructor to prevent instantiation
    private GameManager() {
        GameTime.getInstance();
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

    public static void startGame() {
        System.out.println("Game started");
        GameTime.getInstance().startTimer();

        getInstance().gameThread.start();
    }

    public static void stopGame() {
        running = false;
        GameTime.getInstance().stopTimer();
    }

    private synchronized void lauchGame() {

        while (running) {
            try {
                while (!time_updated) {
                    wait();
                }
                time_updated = false;
                updateGame();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Game loop interrupted");
            }
        }
    }

    public static void addBuilding(BuildingType type, int x, int y) {
        Building building = BuildingFactory.createBuilding(type, x, y);

        if (!GridMap.placeBuilding(building, x, y)) {
            throw new IllegalStateException("Can't place building at " + x + ", "
                    + y);
        }

        getInstance().addBuilding(building);
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public static void removeBuilding(int x, int y) {
        // Find the building at the given coordinates (checking size and position)
        Building building = getInstance().findBuilding(x, y);
        if (building == null) {
            throw new IllegalStateException("No building found at " + x + ", " + y);
        }
        getInstance().removeBuilding(building);
    }

    private Building findBuilding(int x, int y) {
        for (Building building : buildings) {
            if (building.getX() == x && building.getY() == y) {
                return building;
            }
        }
        return null;
    }

    private void removeBuilding(Building building) {
        buildings.remove(building);
        GridMap.removeBuilding(building);
        building.remove();
    }

    public static void addResident() {
        if (PlayerInventory.getRessourceQuantity(Ressource.RESIDENTS) + 1
                > PlayerInventory.getRessourceQuantity(Ressource.MAXRESIDENTS)) {
            throw new IllegalStateException("Not enough place where a resident can stay");
        }

        Resident resident = new Resident();
        getInstance().addResident(resident);
    }

    private void addResident(Resident resident) {
        residents.add(resident);
        unemployed_residents.add(resident);
        System.out.println("Resident added");
    }

    public void removeResident(Resident resident) {
        residents.remove(resident);
        unemployed_residents.remove(resident);
        working_residents.remove(resident);
    }

    public static void assignWorkerToBuilding(Building building) {
        getInstance().assignWorkerToBuilding(building, getInstance().getUnemployedResidents());
    }

    private Resident getUnemployedResidents() {
        if (unemployed_residents.isEmpty()) {
            throw new IllegalStateException("No unemployed resident");
        }
        return unemployed_residents.get(0);
    }

    private void assignWorkerToBuilding(Building building, Resident unemployed_resident) {
        if (PlayerInventory.getRessourceQuantity(Ressource.TOOLS) < 1) {
            throw new IllegalStateException("No tool available");
        }

        if (building.addWorkers(unemployed_resident) > 0) {
            throw new IllegalStateException("The building is full");
        }

        unemployed_residents.remove(unemployed_resident);
        working_residents.add(unemployed_resident);

        unemployed_resident.setWorkplace(building);
    }

    public static void removeWorkerFromBuilding(Building building) {
        getInstance().FireWorkerFromBuilding(building);
    }

    private void FireWorkerFromBuilding(Building building) {
        Resident worker = building.fireWorker();
        // Moves the worker back to the unemployed list
        unemployed_residents.add(worker);
        working_residents.remove(worker);

        worker.fire();
        System.out.println("Worker fired");
        worker.print();
        building.printBuilding();
    }

    public static void updateNight() {
        getInstance().night_updated = true;
    }

    public synchronized void updateTime() {
        getInstance().time_updated = true;
        notify();
    }

    private void updateGame() {
        checkNight();
        updateResidents();
        updateBuildings();
    }

    private void checkNight() {
        if (night_updated) {
            night_updated = false;
            if (GameTime.isNight()) {
                System.out.println("Night time");
            } else {
                System.out.println("Day time");
            }
        }
    }

    private void updateResidents() {
        if (residents.isEmpty()) {
            return;
        }

        if (GameTime.isNight()) {
            for (Resident resident : residents) {
                resident.updateNight();
            }
        } else {
            for (Resident resident : residents) {
                resident.updateDay();
            }
        }
    }

    private void updateBuildings() {
        for (Building building : buildings) {
            building.update();
        }
    }

    public static List<Building> getBuildings() {
        return getInstance().buildings;
    }

}
