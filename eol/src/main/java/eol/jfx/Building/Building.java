package eol.jfx.Building;

import java.util.HashMap;

import eol.jfx.ressources.Ressource;

public abstract class Building {
    public int x, y;
    private int width, height;

    private int maxResidents;
    public int currentResidents;

    private int maxWorkers;
    public int currentWorkers;

    // Hashmap of ressources for construction cost
    private HashMap<Ressource, Integer> constructionCost;
    private int constructionTime;

    public Building(int x, int y, int width, int height, int maxResidents, int maxWorkers, int constructionTime) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxResidents = maxResidents;
        this.currentResidents = 0;
        this.maxWorkers = maxWorkers;
        this.currentWorkers = 0;
        this.constructionTime = constructionTime;
    }
}
