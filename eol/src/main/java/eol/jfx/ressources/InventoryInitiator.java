package eol.jfx.ressources;

import java.util.HashMap;

import eol.jfx.managers.Difficulty;

public class InventoryInitiator {

    public static void initializeInventory(Difficulty difficulty) {
        HashMap<Ressource, Integer> inventory = PlayerInventory.getInventory();

        // Clear the existing inventory
        inventory.clear();

        // Add default values for each resource
        for (Ressource ressource : Ressource.values()) {
            inventory.put(ressource, 0);
        }

        // Depending on difficulty, add more resources
        switch (difficulty) {
            case EASY:
                inventory.put(Ressource.FOOD, 100);
                inventory.put(Ressource.WOOD, 100);
                inventory.put(Ressource.STONE, 100);
                break;
            case MEDIUM:
                inventory.put(Ressource.FOOD, 50);
                inventory.put(Ressource.WOOD, 50);
                inventory.put(Ressource.STONE, 50);
                break;
            case HARD:
                inventory.put(Ressource.FOOD, 25);
                inventory.put(Ressource.WOOD, 25);
                inventory.put(Ressource.STONE, 25);
                break;
        }
    }
}
