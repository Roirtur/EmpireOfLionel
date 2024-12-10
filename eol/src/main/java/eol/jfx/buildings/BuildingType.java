package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.ressources.Ressource;

public enum BuildingType {
    APARTMENT(4, 4),
    CEMENTPLANT(3, 3),
    FARM(2, 2),
    HOUSE(1, 1),
    LUMBERMILL(3, 3),
    QUARY(3, 3),
    STEELMILL(3, 3),
    TOOLFACTORY(2, 2),
    WOODENCABIN(1, 1);

    private final int width;
    private final int height;

    BuildingType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Cost for buildings
    public HashMap<Ressource, Integer> getCost() {
        HashMap<Ressource, Integer> cost = new HashMap<>();
        switch (this) {
            case APARTMENT:
                cost.put(Ressource.STEEL, 50);
                cost.put(Ressource.STONE, 150);
                cost.put(Ressource.CEMENT, 20);
                break;
            case CEMENTPLANT:
                cost.put(Ressource.STEEL, 20);
                cost.put(Ressource.STONE, 50);
                break;
            case FARM:
                cost.put(Ressource.WOOD, 30);
                cost.put(Ressource.STONE, 30);
                break;
            case HOUSE:
                cost.put(Ressource.WOOD, 50);
                cost.put(Ressource.STONE, 50);
                break;
            case LUMBERMILL:
                cost.put(Ressource.WOOD, 100);
                cost.put(Ressource.STONE, 50);
                break;
            case QUARY:
                cost.put(Ressource.WOOD, 150);
                break;
            case STEELMILL:
                cost.put(Ressource.IRON, 50);
                cost.put(Ressource.STONE, 150);
                break;
            case TOOLFACTORY:
                cost.put(Ressource.WOOD, 50);
                cost.put(Ressource.STONE, 50);
                break;
            case WOODENCABIN:
                cost.put(Ressource.WOOD, 20);
                break;
        }
        return cost;
    }

    // Maximum residents for buildings
    public int getMaxResidents() {
        switch (this) {
            case APARTMENT:
                return 100;
            case CEMENTPLANT:
                return 0;
            case FARM:
                return 0;
            case HOUSE:
                return 10;
            case LUMBERMILL:
                return 0;
            case QUARY:
                return 0;
            case STEELMILL:
                return 0;
            case TOOLFACTORY:
                return 0;
            case WOODENCABIN:
                return 0;
            default:
                return 0;
        }
    }

    // Maximum workers for buildings
    public int getMaxWorkers() {
        switch (this) {
            case APARTMENT:
                return 0;
            case CEMENTPLANT:
                return 20;
            case FARM:
                return 5;
            case HOUSE:
                return 0;
            case LUMBERMILL:
                return 15;
            case QUARY:
                return 30;
            case STEELMILL:
                return 20;
            case TOOLFACTORY:
                return 10;
            case WOODENCABIN:
                return 5;
            default:
                return 0;
        }
    }

    // Construction time for buildings (in ingame days)
    public int getConstructionTime() {
        switch (this) {
            case APARTMENT:
                return 5;
            case CEMENTPLANT:
                return 3;
            case FARM:
                return 2;
            case HOUSE:
                return 1;
            case LUMBERMILL:
                return 2;
            case QUARY:
                return 3;
            case STEELMILL:
                return 4;
            case TOOLFACTORY:
                return 3;
            case WOODENCABIN:
                return 1;
            default:
                return 0;
        }
    }

    // Upgrade cost for buildings (they all have the same cost)
    // Default upgrade cost at 10 wood, 10 stone and 3 iron
    public static HashMap<Ressource, Integer> getUpgradeCost() {
        HashMap<Ressource, Integer> upgradeCost = new HashMap<>();
        upgradeCost.put(Ressource.WOOD, 10);
        upgradeCost.put(Ressource.STONE, 10);
        upgradeCost.put(Ressource.IRON, 3);
        return upgradeCost;
    }
}
