package eol.jfx.ressources;

import eol.jfx.gamesettings.Difficulty;

public class InventoryInitiator {

    public static void initializeInventory(Difficulty difficulty) {
        // Clear the existing inventory
        PlayerInventory.clear();

        // Add default values for each resource
        for (Ressource ressource : Ressource.values()) {
            PlayerInventory.setRessourceQuantity(ressource, 0);
        }

        PlayerInventory.setRessourceQuantity(Ressource.RESIDENTS, 5);
        // Depending on difficulty, add more resources
        switch (difficulty) {
            case EASY:
                PlayerInventory.setRessourceQuantity(Ressource.FOOD, 100);
                PlayerInventory.setRessourceQuantity(Ressource.WOOD, 100);
                PlayerInventory.setRessourceQuantity(Ressource.STONE, 100);
                break;
            case MEDIUM:
                PlayerInventory.setRessourceQuantity(Ressource.FOOD, 50);
                PlayerInventory.setRessourceQuantity(Ressource.WOOD, 50);
                PlayerInventory.setRessourceQuantity(Ressource.STONE, 50);
                break;
            case HARD:
                PlayerInventory.setRessourceQuantity(Ressource.FOOD, 20);
                PlayerInventory.setRessourceQuantity(Ressource.WOOD, 20);
                PlayerInventory.setRessourceQuantity(Ressource.STONE, 20);
                break;
        }
    }
}
