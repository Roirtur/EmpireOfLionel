package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class Miner extends Work {

    @Override
    public String toString() {
        return "Miner";
    }

    @Override
    public void work() {
        isWorking = true;
        // TODO Mine at the Quarry

        // When the work is done, generate income
        generateRessources();
    }

    @Override
    public void generateRessources() {
        // Chose randomly between these, with odds
        PlayerInventory.productRessource(Ressource.STONE, 1);
        PlayerInventory.productRessource(Ressource.COAL, 1);
        PlayerInventory.productRessource(Ressource.IRON, 1);
    }
}
