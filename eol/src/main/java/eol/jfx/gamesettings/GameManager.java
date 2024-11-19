package eol.jfx.gamesettings;

import java.util.List;

import eol.jfx.buildings.Building;
import eol.jfx.residents.Resident;

public class GameManager {
  private List<Building> buildings;
  private List<Resident> residents;

  public void addBuilding(Building building) { buildings.add(building); }

  public void removeBuilding(Building building) { buildings.remove(building); }

  public void addResident(Resident resident) { residents.add(resident); }

  public void removeResident(Resident resident) { residents.remove(resident); }

  public void assignWorkerToBuilding(Resident resident, Building building) {
    if (building.addWorkers(1) > 0) {
      throw new IllegalStateException("The building is full");
    }
    resident.setWorkplace(building);
  }

  public void updateResources() {
    // TODO
  }

  public void updateHunger() {
    for (Resident resident : residents) {
      resident.consumeFood();
    }
  }

  public void updateGame() {
    updateResources();
    updateHunger();
  }
}
