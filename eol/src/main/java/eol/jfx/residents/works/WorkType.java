package eol.jfx.residents.works;

import java.util.HashMap;

import eol.jfx.ressources.Ressource;

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
                consumedRessources.put(Ressource.STEEL, 1);
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
                return 1000;
            case CEMENTPRODUCTOR:
                return 1000;
            case FARMER:
                return 1000;
            case LUMBERJACK:
                return 1000;
            case MINER:
                return 1000;
            case TOOLMAKER:
                return 1000;
            case UNEMPLOYED:
                return 1000;
            case WOODWORKER:
                return 1000;
        }

        return 0;
    }
}
