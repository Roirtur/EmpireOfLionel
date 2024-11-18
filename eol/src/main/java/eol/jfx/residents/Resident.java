package eol.jfx.residents;

import eol.jfx.residents.works.Work;

public class Resident {
    private int age;
    private int hunger;

    private boolean isAlive;
    private boolean hasTool;

    // private Building house;
    // private Building workplace;
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
}
