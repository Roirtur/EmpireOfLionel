package eol.jfx.buildings;

import eol.jfx.managers.GameManager;
import eol.jfx.observers.Observer;
import eol.jfx.residents.Resident;
import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

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
  private final HashMap<Ressource, Integer> upgradeCost =
      BuildingType.getUpgradeCost();

  private final static List<Observer> observers = new ArrayList<>();

  public static void addObserver(Observer observer) { observers.add(observer); }

  public void removeObserver(Observer observer) { observers.remove(observer); }

  private void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }

  public Building(int x, int y, int width, int height, int maxResidents,
                  int maxWorkers, int constructionTime,
                  HashMap<Ressource, Integer> constructionCost) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.maxResidents = maxResidents;

    this.currentResidents = 0;
    this.maxWorkers = maxWorkers;
    this.currentWorkers = 0;
    this.constructionTime = constructionTime * 24;
    this.constructionCost = constructionCost;
  }

  private boolean isBuildable() {
    boolean enoughRessources =
        PlayerInventory.hasEnoughRessources(constructionCost);
    return enoughRessources;
  }

  public void build() {
    if (!isBuildable()) {
      throw new IllegalArgumentException(
          "The building is not buildable (not enough ressources)");
    }

    useRessources(constructionCost);
    // System.out.println("Construction cost: " + constructionCost);
  }

  public void remove() {
    // Reassign a portion of the construction cost back to the player
    for (Ressource ressource : constructionCost.keySet()) {
      int quantity = constructionCost.get(ressource);
      int refundQuantity = (int)(quantity * 0.5); // Refund 50% of the resources
      PlayerInventory.productRessource(ressource, refundQuantity);
    }

    // Fire all the workers
    while (!residentPile.isEmpty()) {
      GameManager.removeWorkerFromBuilding(this);
    }

    // Don't do anything if residents exceed maxResidents after removing the
    // building Remove the building
    exists = false;
  }

  public int getWidth() { return width; }

  public int getHeight() { return height; }

  public int getX() { return x; }

  public int getY() { return y; }

  public int addResident(int quantity) {
    if (!isBuilt) {
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
    if (!isBuilt) {
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

    return 0;
  }

  public Resident fireWorker() {
    if (!isBuilt) {
      throw new IllegalArgumentException("The building is not built yet");
    }

    if (currentWorkers == 0) {
      throw new IllegalArgumentException(
          "There are no workers in the building");
    }

    currentWorkers--;
    // Give back the tools
    PlayerInventory.productRessource(Ressource.TOOLS);
    return residentPile.pop();
  }

  public void upgradeBuilding(UpgradeType type) {
    if (!isBuilt) {
      throw new IllegalArgumentException("The building is not built yet");
    }

    if (upgrades >= maxUpgrades) {
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
      throw new IllegalArgumentException("The building does not exist");
    }
    if (!PlayerInventory.hasEnoughRessources(ressources)) {
      throw new IllegalArgumentException(
          "The player does not have enough ressources");
    }

    // Remove the ressources from the player inventory
    PlayerInventory.useRessources(ressources);
  }

  public void update() {
    if (!isBuilt) {
      if (constructionProgress >= constructionTime) {
        isBuilt = true;
        PlayerInventory.productRessource(Ressource.MAXRESIDENTS, maxResidents);
        notifyObservers();
      } else {
        constructionProgress++;
      }
    }
  }

  public int getMaxResidents() { return maxResidents; }

  public int getCurrentResidents() { return currentResidents; }

  public int getMaxWorkers() { return maxWorkers; }

  public int getCurrentWorkers() { return currentWorkers; }
}
