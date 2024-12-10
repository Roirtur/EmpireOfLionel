package eol.jfx.residents.works;

import java.util.HashMap;
import java.util.Map;

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
        // System.out.println("Working on wood...");
        // TODO Go take wood from the woodencabin, and come back

        // When the work is done, generate income
        new Thread(() -> {
            try {
                Thread.sleep(WorkType.LUMBERJACK.getProductionTime());
                generateRessources();
                // System.out.println("Woodworkers finished working");
                isWorking = false;
            } catch (InterruptedException e) {
            }
        }).start();
    }

    @Override
    public void generateRessources() {
        HashMap< Ressource, Integer> producedRessources = WorkType.LUMBERJACK.getProducedRessources();
        for (Map.Entry< Ressource, Integer> entry : producedRessources.entrySet()) {
            PlayerInventory.productRessource(entry.getKey(), entry.getValue());
        }

        HashMap< Ressource, Integer> consumedRessources = WorkType.LUMBERJACK.getConsumedRessources();
        for (Map.Entry< Ressource, Integer> entry : consumedRessources.entrySet()) {
            PlayerInventory.useRessource(entry.getKey(), entry.getValue());
        }
    }
}
