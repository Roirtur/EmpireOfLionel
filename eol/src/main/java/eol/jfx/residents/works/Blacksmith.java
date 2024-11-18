package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class Blacksmith extends Work {
    
    @Override
    public String toString() {
        return "Blacksmith";
    }

    @Override
    public void work() {
        // TODO Go take iron and coal from the quarry, and come back
        // TODO Take away ressources from the player inventory when taking them

        // When the work is done, generate income
        generateRessources();
    }

    @Override
    public void generateRessources() {
        PlayerInventory.productRessource(Ressource.STEEL, 1);
    }
}
