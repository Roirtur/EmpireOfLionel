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

    public static void useRessources(java.util.HashMap<Ressource, Integer> ressources) {
        for (Ressource ressource : ressources.keySet()) {
            useRessource(ressource, ressources.get(ressource));
        }
    }

    public static int getRessourceQuantity(Ressource ressource) {
        return inventory.getOrDefault(ressource, 0);
    }

    public static void setRessourceQuantity(Ressource ressource, int quantity) {
        inventory.put(ressource, quantity);
    }
    
    public static void clear() {
        inventory.clear();
    }

    public static boolean hasEnoughRessources(java.util.HashMap<Ressource, Integer> ressources) {
        for (Ressource ressource : ressources.keySet()) {
            if (!inventory.containsKey(ressource) || inventory.get(ressource) < ressources.get(ressource)) {
                return false;
            }
        }
        return true;
    }

    public static void print() {
        for (Ressource ressource : inventory.keySet()) {
            System.out.println(ressource + ": " + inventory.get(ressource));
        }
    }
}
