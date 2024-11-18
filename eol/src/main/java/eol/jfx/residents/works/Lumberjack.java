package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class Lumberjack extends Work {

    @Override
    public String toString() {
        return "Lumberjack";
    }

    @Override
    public void work() {
        isWorking = true;
        // TODO Go cut trees at the forest

        // When the work is done, generate income
        generateRessources();
    }

    @Override
    public void generateRessources() {
        PlayerInventory.productRessource(Ressource.WOOD, 1);
    }
    
}
