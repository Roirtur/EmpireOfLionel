package eol.jfx.ressources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eol.jfx.observers.Observer;
import javafx.application.Platform;

public class PlayerInventory {

  // We use Singleton pattern to make sure that we have only one instance of the
  // inventory
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

  // We set here the inventory of the player, with the quantity of each
  // ressource in a dictionary
  private static final HashMap<Ressource, Integer> inventory = new HashMap<>();
  private static final List<Observer> observers = new ArrayList<>();

  public static void registerObserver(Observer observer) {
    observers.add(observer);
  }

  public static void unregisterObserver(Observer observer) {
    observers.remove(observer);
  }

  private static void notifyObservers() {
    Platform.runLater(() -> {
      for (Observer observer : observers) {
        observer.update();
      }
    });
  }

  public static void productRessource(Ressource ressource) {
    productRessource(ressource, 1);
  }

  public static void productRessource(Ressource ressource, int quantity) {
    inventory.put(ressource, inventory.getOrDefault(ressource, 0) + quantity);
    notifyObservers();
  }

  public static void useRessource(Ressource ressource) {
    useRessource(ressource, 1);
  }

  public static void useRessource(Ressource ressource, int quantity) {
    if (!inventory.containsKey(ressource) ||
        inventory.get(ressource) < quantity) {
      // We can't use a ressource that we don't have
      throw new IllegalArgumentException("You don't have this ressource");
    } else {
      inventory.put(ressource, inventory.get(ressource) - quantity);
      notifyObservers();
    }
  }

  public static void
  useRessources(java.util.HashMap<Ressource, Integer> ressources) {
    for (Ressource ressource : ressources.keySet()) {
      useRessource(ressource, ressources.get(ressource));
    }
  }

  public static int getRessourceQuantity(Ressource ressource) {
    return inventory.getOrDefault(ressource, 0);
  }

  public static void setRessourceQuantity(Ressource ressource, int quantity) {
    inventory.put(ressource, quantity);
    notifyObservers();
  }

  public static void clear() {
    inventory.clear();
    notifyObservers();
  }

  public static boolean
  hasEnoughRessources(java.util.HashMap<Ressource, Integer> ressources) {
    for (Ressource ressource : ressources.keySet()) {
      if (!inventory.containsKey(ressource) ||
          inventory.get(ressource) < ressources.get(ressource)) {
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
