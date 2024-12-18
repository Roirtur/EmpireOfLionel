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

        // Depending on difficulty, add more resources
        switch (difficulty) {
            case EASY:
                PlayerInventory.setRessourceQuantity(Ressource.FOOD, 1000);
                PlayerInventory.setRessourceQuantity(Ressource.WOOD, 100);
                PlayerInventory.setRessourceQuantity(Ressource.STONE, 100);
                PlayerInventory.setRessourceQuantity(Ressource.TOOLS, 20);
                PlayerInventory.setRessourceQuantity(Ressource.MAXRESIDENTS, 20);

                break;
            case MEDIUM:
                PlayerInventory.setRessourceQuantity(Ressource.FOOD, 500);
                PlayerInventory.setRessourceQuantity(Ressource.WOOD, 50);
                PlayerInventory.setRessourceQuantity(Ressource.STONE, 50);
                PlayerInventory.setRessourceQuantity(Ressource.MAXRESIDENTS, 10);

                break;
            case HARD:
                PlayerInventory.setRessourceQuantity(Ressource.FOOD, 200);
                PlayerInventory.setRessourceQuantity(Ressource.WOOD, 20);
                PlayerInventory.setRessourceQuantity(Ressource.STONE, 20);
                PlayerInventory.setRessourceQuantity(Ressource.MAXRESIDENTS, 5);

                break;
            case GODMOD:
                PlayerInventory.setRessourceQuantity(Ressource.FOOD, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.WOOD, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.STONE, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.CEMENT, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.COAL, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.IRON, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.LUMBER, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.TOOLS, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.STEEL, 10000);
                PlayerInventory.setRessourceQuantity(Ressource.MAXRESIDENTS, 1000);
                break;
        }
    }
}
