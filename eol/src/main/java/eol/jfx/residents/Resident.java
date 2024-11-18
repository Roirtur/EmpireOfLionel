package eol.jfx.residents;

import eol.jfx.buildings.Building;
import eol.jfx.residents.works.Work;

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
        this.age = 0;
        this.hunger = 100;
        this.isAlive = true;
        this.hasTool = false;

        this.x = x;
        this.y = y;
    }

    public void setHouse(Building house) {
        this.house = house;
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
        this.work = workplace.getWorkerType();
    }

    public void giveTool() {
        this.hasTool = true;
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
