package eol.jfx.residents.works;

import java.util.HashMap;
import java.util.Map;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class ToolMaker extends Work {

    @Override
    public String toString() {
        return "ToolMaker";
    }

    @Override
    public void work() {
        isWorking = true;
        // System.out.println("Working on tools...");
        // TODO Go take steel from the SteelMill, and coal from the CoalMine, and
        // come back

        // When the work is done, generate income
        new Thread(() -> {
            try {
                Thread.sleep(WorkType.TOOLMAKER.getProductionTime());
                generateRessources();
                // System.out.println("ToolMaker finished working");
                isWorking = false;
            } catch (InterruptedException e) {
            }
        }).start();
    }

    @Override
    public void generateRessources() {
        HashMap< Ressource, Integer> producedRessources = WorkType.TOOLMAKER.getProducedRessources();
        for (Map.Entry< Ressource, Integer> entry : producedRessources.entrySet()) {
            PlayerInventory.productRessource(entry.getKey(), entry.getValue());
        }

        HashMap< Ressource, Integer> consumedRessources = WorkType.TOOLMAKER.getConsumedRessources();
        for (Map.Entry< Ressource, Integer> entry : consumedRessources.entrySet()) {
            PlayerInventory.useRessource(entry.getKey(), entry.getValue());
        }
    }
}
