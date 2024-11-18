package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class Woodworkers extends Work {

    @Override
    public String toString() {
        return "Woodworkers";
    }

    @Override
    public void work() {
        isWorking = true;
        // TODO Go take wood from the woodencabin, and come back
        // TODO Take away ressources from the player inventory when taking them

        // When the work is done, generate income
        generateRessources();
    }

    @Override
    public void generateRessources() {
        PlayerInventory.productRessource(Ressource.LUMBER, 1);
    }
}
