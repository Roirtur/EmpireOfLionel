package eol.jfx.residents.works;

import eol.jfx.ressources.Ressource;
import java.util.HashMap;

public enum WorkType {
  BLACKSMITH,
  CEMENTPRODUCTOR,
  FARMER,
  LUMBERJACK,
  MINER,
  TOOLMAKER,
  UNEMPLOYED,
  WOODWORKER;

  public HashMap<Ressource, Integer> getProducedRessources() {
    HashMap<Ressource, Integer> producedRessources = new HashMap<>();
    switch (this) {
    case BLACKSMITH:
      producedRessources.put(Ressource.STEEL, 1);
      break;
    case CEMENTPRODUCTOR:
      producedRessources.put(Ressource.CEMENT, 1);
      break;
    case FARMER:
      producedRessources.put(Ressource.FOOD, 1);
      break;
    case LUMBERJACK:
      producedRessources.put(Ressource.WOOD, 1);
      break;
    case MINER:
      double random = Math.random();
      Ressource currentRessource;
      // 40% chance for stone, 35% for coal, 25% for iron
      if (random < 0.4) {
        currentRessource = Ressource.STONE;
      } else if (random < 0.75) {
        currentRessource = Ressource.COAL;
      } else {
        currentRessource = Ressource.IRON;
      }

      producedRessources.put(currentRessource, 1);
      break;
    case TOOLMAKER:
      producedRessources.put(Ressource.TOOLS, 1);
      break;
    case UNEMPLOYED:
      break;
    case WOODWORKER:
      producedRessources.put(Ressource.LUMBER, 1);
      break;
    }

    return producedRessources;
  }

  // Consumed ressources when working
  public HashMap<Ressource, Integer> getConsumedRessources() {
    HashMap<Ressource, Integer> consumedRessources = new HashMap<>();

    switch (this) {
    case BLACKSMITH:
      consumedRessources.put(Ressource.IRON, 1);
      break;
    case CEMENTPRODUCTOR:
      consumedRessources.put(Ressource.COAL, 1);
      consumedRessources.put(Ressource.STONE, 1);
      break;
    case FARMER:
      break;
    case LUMBERJACK:
      break;
    case MINER:
      break;
    case TOOLMAKER:
      consumedRessources.put(Ressource.IRON, 1);
      consumedRessources.put(Ressource.COAL, 1);
      break;
    case UNEMPLOYED:
      break;
    case WOODWORKER:
      consumedRessources.put(Ressource.WOOD, 1);
      break;
    }

    return consumedRessources;
  }

  public int getProductionTime() {
    switch (this) {
    case BLACKSMITH:
      return 1; // 1 hour of game time
    case CEMENTPRODUCTOR:
      return 1;
    case FARMER:
      return 1;
    case LUMBERJACK:
      return 1;
    case MINER:
      return 1;
    case TOOLMAKER:
      return 1;
    case UNEMPLOYED:
      return 1;
    case WOODWORKER:
      return 1;
    }

    return 0;
  }
}