package eol.jfx.buildings;

import java.util.HashMap;
import java.util.Stack;

import eol.jfx.residents.Resident;
import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public abstract class Building {

    private final int x, y;
    private final int width, height;

    private boolean exists = true;

    private int maxResidents;
    public int currentResidents;

    private int maxWorkers;
    public int currentWorkers;
    // Pile of residents
    private final Stack<Resident> residentPile = new Stack<>();

    // Hashmap of ressources for construction cost
    private final HashMap<Ressource, Integer> constructionCost;
    private final int constructionTime;
    private int constructionProgress = 0;

    public boolean isBuilt = false;

    private WorkType workertype;

    private int upgrades = 0;
    private final int maxUpgrades = 3;

    // Default upgrade cost
    private final HashMap<Ressource, Integer> upgradeCost = new HashMap<>() {
        {
            put(Ressource.WOOD, 10);
            put(Ressource.STONE, 10);
            put(Ressource.IRON, 3);
        }
    };

    public Building(int x, int y, int width, int height, int maxResidents,
            int maxWorkers, int constructionTime,
            HashMap<Ressource, Integer> constructionCost) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxResidents = maxResidents;
        PlayerInventory.productRessource(Ressource.MAXRESIDENTS, maxResidents);

        this.currentResidents = 0;
        this.maxWorkers = maxWorkers;
        this.currentWorkers = 0;
        this.constructionTime = constructionTime * 24;
        this.constructionCost = constructionCost;

        System.out.println("Building created");
        build();
    }

    private boolean isBuildable() {
        // Check if the building is buildable

        // Check if the player has enough ressources
        boolean enoughRessources
                = PlayerInventory.hasEnoughRessources(constructionCost);

        // Position check is handled by Map
        return enoughRessources;
    }

    private void build() {
        // Check if the building is buildable
        if (!isBuildable()) {
            // Throw an exception
            throw new IllegalArgumentException("The building is not buildable");
        }

        useRessources(constructionCost);
    }

    public void remove() {
        // Remove the building
        exists = false;
        // TODO do things only if exists on other functions
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int addResident(int quantity) {
        // Check if the building is built
        if (!isBuilt) {
            // Throw an exception
            throw new IllegalArgumentException("The building is not built yet");
        }

        // Check if the building has enough space
        if (currentResidents + quantity > maxResidents) {
            // Add as many residents as possible and return remaining quantity
            int remainingResidents = maxResidents - currentResidents;
            currentResidents += remainingResidents;

            return quantity - remainingResidents;
        }

        // Add the residents
        currentResidents += quantity;

        return 0;
    }

    public int addWorkers(Resident resident) {
        // Check if the building is built
        if (!isBuilt) {
            // Throw an exception
            throw new IllegalArgumentException("The building is not built yet");
        }

        // Check if the building has enough space
        if (currentWorkers + 1 > maxWorkers) {
            // Add as many workers as possible and return remaining quantity
            int remainingWorkers = maxWorkers - currentWorkers;
            currentWorkers += remainingWorkers;

            return 1;
        }
        // Add the workers
        currentWorkers += 1;
        residentPile.push(resident);

        // Print out the resident pile
        System.out.println("Resident pile: " + residentPile.size());
        System.out.println("Current workers: " + currentWorkers);
        for (Resident r : residentPile) {
            System.out.println(r);
        }

        return 0;
    }

    public Resident fireWorker() {
        // Check if the building is built
        if (!isBuilt) {
            // Throw an exception
            throw new IllegalArgumentException("The building is not built yet");
        }

        // Check if there are workers
        if (currentWorkers == 0) {
            // Throw an exception
            throw new IllegalArgumentException("There are no workers in the building");
        }

        // Remove a worker
        currentWorkers--;
        return residentPile.pop();
    }

    public void upgradeBuilding(UpgradeType type) {
        // Upgrade the building

        // Check if the building is built
        if (!isBuilt) {
            // Throw an exception
            throw new IllegalArgumentException("The building is not built yet");
        }

        if (upgrades >= maxUpgrades) {
            // Throw an exception
            throw new IllegalArgumentException(
                    "The building is already at max level");
        }

        // Remove the ressources from the player inventory
        useRessources(upgradeCost);
        upgrades++;

        // Upgrade the building
        switch (type) {
            case RESIDENTS:
                // Increase the number of residents
                this.maxResidents += 5;
                PlayerInventory.productRessource(Ressource.MAXRESIDENTS, 5);
                break;
            case WORKERS:
                // Increase the size of the building
                this.maxWorkers += 5;
                break;
        }
    }

    public WorkType getWorkerType() {
        // Return the type of worker that can work in this building
        return workertype;
    }

    public void printBuilding() {
        System.out.println("Building at (" + x + ", " + y + ")");
        System.out.println("Cost: " + constructionCost);
        System.out.println("Size: " + width + "x" + height);
        System.out.println("Residents: " + currentResidents + "/" + maxResidents);
        System.out.println("Workers: " + currentWorkers + "/" + maxWorkers);
    }

    private void useRessources(HashMap<Ressource, Integer> ressources) {
        if (!exists) {
            // Throw an exception
            throw new IllegalArgumentException("The building does not exist");
        }

        // Check if the player has enough ressources
        if (!PlayerInventory.hasEnoughRessources(upgradeCost)) {
            // Throw an exception
            throw new IllegalArgumentException(
                    "The player does not have enough ressources to upgrade the "
                    + "building");
        }

        // Remove the ressources from the player inventory
        PlayerInventory.useRessources(ressources);
    }

    public void update() {
        // Update the building
        if (!isBuilt) {
            // Check if the building is built
            if (constructionProgress >= constructionTime) {
                isBuilt = true;
                System.out.println("Building is built");
            } else {
                constructionProgress++;
            }
        }
    }
}
