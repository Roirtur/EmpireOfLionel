package eol.jfx.residents;

import java.util.List;

import eol.jfx.buildings.Building;
import eol.jfx.residents.works.Work;
import eol.jfx.residents.works.WorkFactory;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class Resident {

    private int age;
    private int hunger;

    private boolean isAlive;

    private Building house;
    private Building workplace;
    private Work work;

    public int x, y;

    public Resident() {
        // Test if enough player ressources
        if (PlayerInventory.getRessourceQuantity(Ressource.RESIDENTS) + 1
                > PlayerInventory.getRessourceQuantity(Ressource.MAXRESIDENTS)) {
            throw new IllegalStateException(
                    "Not enough ressources to create a resident");
        }

        PlayerInventory.productRessource(Ressource.RESIDENTS);

        this.age = 0;
        this.hunger = 100;
        this.isAlive = true;

        // Random position from 100 to 500
        this.x = (int) (Math.random() * 400) + 100;
        this.y = (int) (Math.random() * 400) + 100;
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
        if (workplace == null) {
            this.work = null;
            return;
        }

        this.workplace = workplace;
        this.work = WorkFactory.createWork(workplace.getWorkerType());
        System.out.println("New work: " + work.toString());
        print();
    }

    public void fire() {
        if (workplace == null) {
            return;
        }

        workplace = null;
        work = null;
    }

    private void eat() {
        if (PlayerInventory.getRessourceQuantity(Ressource.FOOD) <= 0) {
            return;
        }

        // System.out.println("Resident is eating");
        PlayerInventory.useRessource(Ressource.FOOD);
        hunger += 50;
        if (hunger > 100) {
            hunger = 100;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void updateHunger() {
        hunger -= 5;

        if (hunger <= 40) {
            eat();
        }
        if (hunger <= 0) {
            isAlive = false;
        }
    }

    public void updateDay() {
        // TODO: Update age and hunger once in a while
        // TODO: Go to house when it is night
        if (workplace != null && work != null && !work.isWorking) {
            work.work();
        }

        updateHunger();
    }

    public void updateNight() {
        // Do not stop work, it stops by itself when task is done
        updateHunger();
    }

    public void print() {
        System.out.println("Resident at (" + x + ", " + y + ")");
        System.out.println("Age: " + age);
        System.out.println("Hunger: " + hunger);
        System.out.println("Is alive: " + isAlive);
        System.out.println("House: " + house);
        System.out.println("Workplace: " + workplace);
        if (work != null) {
            System.out.println("Work: " + work.toString());
        }
    }
}
