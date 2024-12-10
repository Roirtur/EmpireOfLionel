package eol.jfx.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import eol.jfx.buildings.Building;
import eol.jfx.buildings.BuildingFactory;
import eol.jfx.buildings.BuildingType;
import eol.jfx.gamesettings.Difficulty;
import eol.jfx.gamesettings.SceneManager;
import eol.jfx.residents.Resident;
import eol.jfx.ressources.InventoryInitiator;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import javafx.application.Platform;

public class GameManager {

    private final List<Building> buildings = new ArrayList<>();
    private final List<Resident> residents = new ArrayList<>();
    private final List<Resident> unemployed_residents = new ArrayList<>();
    private final List<Resident> working_residents = new ArrayList<>();

    private final BlockingQueue<Integer> residentQueue
            = new LinkedBlockingQueue<>();

    public boolean night_updated = false;
    public boolean time_updated = false;

    private static boolean running = true;

    private final Thread gameThread = new Thread(this::lauchGame);

    // The single instance of the class
    private static volatile GameManager instance;

    // Variable to store the selected difficulty
    private static Difficulty selectedDifficulty = Difficulty.EASY;

    // Wining condition
    private static final int REQUIRED_RESIDENTS = 1000;

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
        // System.out.println("Game started with difficulty: " + selectedDifficulty);
        GameTime.getInstance().startTimer();

        getInstance().gameThread.start();
        InventoryInitiator.initializeInventory(selectedDifficulty);

    }

    public static void stopGame() {
        running = false;
        GameTime.getInstance().stopTimer();
    }

    public static void setSelectedDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
    }

    private synchronized void lauchGame() {

        while (running) {
            try {
                while (!time_updated) {
                    wait();
                }
                time_updated = false;
                updateGame();
                processResidentQueue(); // Traiter la file d'attente des résidents
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // System.out.println("Game loop interrupted");
            }
        }
    }

    private void processResidentQueue() {
        Integer quantity;
        while ((quantity = residentQueue.poll()) != null) {
            for (int i = 0; i < quantity; i++) {
                addResident();
                checkVictoryConditions();
            }
        }
    }

    public static void addResident(int quantity) {
        getInstance().residentQueue.offer(quantity);
    }

    public static void addBuilding(BuildingType type, int x, int y) {
        Building building = BuildingFactory.createBuilding(type, x, y);

        if (!GridMap.placeBuilding(building, x, y)) {
            throw new IllegalStateException("Can't place building at " + x + ", "
                    + y);
        }

        if (building == null) {
            throw new IllegalStateException("Building not created");
        }

        getInstance().addBuilding(building);
    }

    public void addBuilding(Building building) {
        try {
            buildings.add(building);
            building.build();
        } catch (Exception e) {
            buildings.remove(building);
            throw new IllegalStateException(e);
        }
    }

    public static void removeBuilding(int x, int y) {
        // Find the building at the given coordinates (checking size and position)
        Building building = getInstance().findBuilding(x, y);
        if (building == null) {
            throw new IllegalStateException("No building found at " + x + ", " + y);
        }
        getInstance().removeBuilding(building);
    }

    public Building findBuilding(int x, int y) {
        for (Building building : buildings) {
            if (building.getX() == x && building.getY() == y) {
                return building;
            }
        }
        return null;
    }

    public void removeBuilding(Building building) {
        GridMap.removeBuilding(building);
        buildings.remove(building);
        building.remove();
    }

    public static void addResident() {
        if (PlayerInventory.getRessourceQuantity(Ressource.RESIDENTS) + 1
                > PlayerInventory.getRessourceQuantity(Ressource.MAXRESIDENTS)) {
            throw new IllegalStateException(
                    "Not enough place where a resident can stay");
        }
        // Check for enough food
        if (PlayerInventory.getRessourceQuantity(Ressource.FOOD) < 1) {
            throw new IllegalStateException("Not enough food available");
        }

        Resident resident = new Resident();
        getInstance().addResident(resident);
    }

    private void addResident(Resident resident) {
        residents.add(resident);
        unemployed_residents.add(resident);
        // System.out.println("Resident added");
    }

    public static void removeResidentStatic(Resident resident) {
        getInstance().removeResident(resident);
    }

    public void removeResident(Resident resident) {
        residents.remove(resident);
        unemployed_residents.remove(resident);
        working_residents.remove(resident);
        PlayerInventory.useRessource(Ressource.RESIDENTS);
    }

    public static void assignWorkerToBuilding(Building building) {
        getInstance().assignWorkerToBuilding(
                building, getInstance().getUnemployedResidents());
    }

    private Resident getUnemployedResidents() {
        if (unemployed_residents.isEmpty()) {
            throw new IllegalStateException("No unemployed resident");
        }
        return unemployed_residents.get(0);
    }

    private void assignWorkerToBuilding(Building building,
            Resident unemployed_resident) {
        if (PlayerInventory.getRessourceQuantity(Ressource.TOOLS) < 1) {
            throw new IllegalStateException("No tool available");
        }

        if (building.addWorkers(unemployed_resident) > 0) {
            throw new IllegalStateException("The building is full");
        }

        PlayerInventory.useRessource(Ressource.TOOLS, 1);

        unemployed_residents.remove(unemployed_resident);
        working_residents.add(unemployed_resident);

        unemployed_resident.setWorkplace(building);
    }

    public static void removeWorkerFromBuilding(Building building) {
        getInstance().FireWorkerFromBuilding(building);
    }

    private void FireWorkerFromBuilding(Building building) {
        Resident worker = building.fireWorker();
        if (worker == null) {
            throw new IllegalStateException("No worker in the building");
        }
        // Moves the worker back to the unemployed list
        unemployed_residents.add(worker);
        working_residents.remove(worker);

        worker.fire();
        // System.out.println("Worker fired");
        // worker.print();
        // building.printBuilding();
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
                // System.out.println("Night time");
            } else {
                // System.out.println("Day time");
            }
        }
    }

    private void updateResidents() {
        if (residents.isEmpty()) {
            return;
        }

        Iterator<Resident> iterator = residents.iterator();
        while (iterator.hasNext()) {
            Resident resident = iterator.next();
            if (GameTime.isNight()) {
                resident.updateNight();
            } else {
                resident.updateDay();
            }
            if (!resident.isAlive()) {
                iterator.remove(); // Utilisez l'itérateur pour supprimer l'élément
                unemployed_residents.remove(resident);
                working_residents.remove(resident);
                PlayerInventory.useRessource(Ressource.RESIDENTS);
                checkDefeatCondition();
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

    private void checkVictoryConditions() {
        if (PlayerInventory.getRessourceQuantity(Ressource.RESIDENTS)
                == REQUIRED_RESIDENTS) {
            onVictory();
        }
    }

    private void onVictory() {
        // System.out.println("You won the game!");
        stopGame();
        Platform.runLater(() -> {
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.showScene("victory");
        });
    }

    private void checkDefeatCondition() {
        if (PlayerInventory.getRessourceQuantity(Ressource.RESIDENTS) == 0) {
            onDefeat();
        }
    }

    private void onDefeat() {
        // System.out.println("You lost the game!");
        stopGame();
        Platform.runLater(() -> {
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.showScene("defeat");
        });
    }

    public List<Resident> getWorking_residents() {
        return working_residents;
    }
}
