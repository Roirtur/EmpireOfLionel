package eol.jfx.ressources;

public class PlayerInventory {

    // We use Singleton pattern to make sure that we have only one instance of the inventory
    private static volatile PlayerInventory instance;

    private PlayerInventory() {}

    public static PlayerInventory getInstance() {
        PlayerInventory localInstance = PlayerInventory.instance;
        if (localInstance == null) {
            // Safely create the instance if it doesn't exist (double-checked locking)
            synchronized (PlayerInventory.class) {
                if (localInstance == null) {
                    PlayerInventory.instance = instance = new PlayerInventory();
                }
            }
        }
        return instance;
    }



    // We set here the inventory of the player, with the quantity of each ressource in a dictionary
    private static final java.util.HashMap<Ressource, Integer> inventory = new java.util.HashMap<>();

    public static java.util.HashMap<Ressource, Integer> getInventory() {
        return inventory;
    }

    public static void productRessource(Ressource ressource) {
        productRessource(ressource, 1);
    }

    public static void productRessource(Ressource ressource, int quantity) {
        if (inventory.containsKey(ressource)) {
            inventory.put(ressource, inventory.get(ressource) + quantity);
        } else {
            inventory.put(ressource, quantity);
        }
    }

    public static void useRessource(Ressource ressource) {
        useRessource(ressource, 1);
    }

    public static void useRessource(Ressource ressource, int quantity) {
        if (!inventory.containsKey(ressource) || inventory.get(ressource) < quantity) {
            // We can't use a ressource that we don't have
            throw new IllegalArgumentException("You don't have this ressource");
        } else {
            inventory.put(ressource, inventory.get(ressource) - quantity);
        }
    }
    
}
