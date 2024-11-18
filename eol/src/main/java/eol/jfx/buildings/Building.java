package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.Work;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public abstract class Building {

    public final int x, y;
    private final int width, height;

    private final int maxResidents;
    public int currentResidents;

    private final int maxWorkers;
    public int currentWorkers;

    // Hashmap of ressources for construction cost
    private final HashMap<Ressource, Integer> constructionCost;
    private final int constructionTime;

    public boolean isBuilt = false;

    private Work workertype;

    public Building(int x, int y, int width, int height, int maxResidents, int maxWorkers, int constructionTime, HashMap<Ressource, Integer> constructionCost) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxResidents = maxResidents;
        this.currentResidents = 0;
        this.maxWorkers = maxWorkers;
        this.currentWorkers = 0;
        this.constructionTime = constructionTime;
        this.constructionCost = constructionCost;

        System.out.println("Building created");
        build();
    }

    private boolean isBuildable() {
        // Check if the building is buildable

        // Check if the player has enough ressources
        boolean enoughRessources = PlayerInventory.hasEnoughRessources(constructionCost);

        // Check if the building is in a valid position
        // TODO: Implement this method so that it do not overlap with other buildings
        boolean validPosition = true;

        return enoughRessources && validPosition;
    }

    private void build() {
        // Check if the building is buildable
        if (!isBuildable()) {
            // Throw an exception
            throw new IllegalArgumentException("The building is not buildable");
        }

        // Remove the ressources from the player inventory
        for (Ressource ressource : constructionCost.keySet()) {
            PlayerInventory.useRessource(ressource, constructionCost.get(ressource));
        }

        System.out.println("Building is being built... (waiting " + constructionTime + " seconds)");
        new Thread(() -> {
            try {
                Thread.sleep(constructionTime * 1000);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted!");
            }
            System.out.println("Building is built!");

            // Set the building as built
            isBuilt = true;
        }).start();
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

    public int addWorkers(int quantity) {
        // Check if the building is built
        if (!isBuilt) {
            // Throw an exception
            throw new IllegalArgumentException("The building is not built yet");
        }

        // Check if the building has enough space
        if (currentWorkers + quantity > maxWorkers) {
            // Add as many workers as possible and return remaining quantity
            int remainingWorkers = maxWorkers - currentWorkers;
            currentWorkers += remainingWorkers;

            return quantity - remainingWorkers;
        }

        // Add the workers
        currentWorkers += quantity;

        return 0;
    }

    public Work getWorkerType() {
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
}
