package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class Farmer extends Work {
    
    @Override
    public String toString() {
        return "Farmer";
    }

    @Override
    public void work() {
        isWorking = true;
        // TODO Farm at the fields

        // When the work is done, generate income
        generateRessources();
    }

    @Override
    public void generateRessources() {
        PlayerInventory.productRessource(Ressource.FOOD, 1);
    }
    
}
