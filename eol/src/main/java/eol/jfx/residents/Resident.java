package eol.jfx.residents;

import eol.jfx.buildings.Building;
import eol.jfx.residents.works.Work;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import java.util.List;

public class Resident {
  private int age;
  private int hunger;

  private boolean isAlive;
  private boolean hasTool;

  private Building house;
  private Building workplace;
  private Work work;

  public int x, y;

  public Resident(int x, int y) {
    // Test if enough player ressources
    if (PlayerInventory.getRessourceQuantity(Ressource.RESIDENTS) + 1 >
        PlayerInventory.getRessourceQuantity(Ressource.MAXRESIDENTS)) {
      throw new IllegalStateException(
          "Not enough ressources to create a resident");
    }

    PlayerInventory.productRessource(Ressource.RESIDENTS);

    this.age = 0;
    this.hunger = 100;
    this.isAlive = true;
    this.hasTool = false;

    this.x = x;
    this.y = y;
  }

  public void setHouse(Building house) {
    if (house == null) {
      this.house = null;
      return;
    }

    this.house = house;
    if (this.house.addResident(1) > 0) {
      this.house = null;
      throw new IllegalStateException("The house is full");
    }
  }

  public boolean findAndSetHouse(List<Building> buildings) {
    for (Building building : buildings) {
      try {
        setHouse(building);
        return true;
      } catch (IllegalStateException e) {
      }
    }
    return false;
  }

  public void setWorkplace(Building workplace) {
    if (!hasTool) {
      throw new IllegalStateException("The resident does not have a tool");
    }

    if (workplace == null) {
      this.work = null;
      return;
    }

    this.workplace = workplace;
    if (this.workplace.addWorkers(1) > 0) {
      this.workplace = null;
      throw new IllegalStateException("The workplace is full");
    }
    this.work = workplace.getWorkerType();
  }

  public void giveTool() {
    if (hasTool) {
      throw new IllegalStateException("The resident already has a tool");
    }

    if (PlayerInventory.getRessourceQuantity(Ressource.TOOLS) <= 0) {
      throw new IllegalStateException("The player does not have any tools");
    }

    PlayerInventory.useRessource(Ressource.TOOLS);
    this.hasTool = true;
  }

  public void consumeFood() {
    if (hunger <= 0) {
      isAlive = false;
      return;
    }

    hunger -= 1;
  }

  public void update() {
    // TODO: Update age and hunger once in a while
    // TODO: Go to house when it is night
    if (work.isWorking) {
      return;
    }

    if (workplace != null) {
      work.work();
    }
  }

  public void print() {
    System.out.println("Resident at (" + x + ", " + y + ")");
    System.out.println("Age: " + age);
    System.out.println("Hunger: " + hunger);
    System.out.println("Is alive: " + isAlive);
    System.out.println("Has tool: " + hasTool);
    System.out.println("House: " + house);
    System.out.println("Workplace: " + workplace);
    System.out.println("Work: " + work.toString());
  }
}
