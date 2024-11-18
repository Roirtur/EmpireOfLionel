package eol.jfx.residents.works;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class ToolMaker extends Work {
    @Override
    public String toString() {
        return "ToolMaker";
    }

    @Override
    public void work() {
        // TODO Go take steel from the SteelMill, and coal from the CoalMine, and come back
        // TODO Take away ressources from the player inventory when taking them

        // When the work is done, generate income
        generateRessources();
    }

    @Override
    public void generateRessources() {
        PlayerInventory.productRessource(Ressource.TOOLS, 1);
    }
}
