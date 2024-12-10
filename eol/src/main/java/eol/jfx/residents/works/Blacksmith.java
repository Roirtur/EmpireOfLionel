package eol.jfx.residents.works;

import java.util.HashMap;
import java.util.Map;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;

public class Blacksmith extends Work {

    @Override
    public String toString() {
        return "Blacksmith";
    }

    @Override
    public void work() {
        isWorking = true;
        // System.out.println("Working on steel...");
        // TODO Go take iron and coal from the quarry, and come back

        // When the work is done, generate income
        new Thread(() -> {
            try {
                Thread.sleep(WorkType.BLACKSMITH.getProductionTime());
                generateRessources();
                // System.out.println("Blacksmith finished working");
                isWorking = false;
            } catch (InterruptedException e) {
            }
        }).start();
    }

    @Override
    public void generateRessources() {
        HashMap< Ressource, Integer> producedRessources = WorkType.BLACKSMITH.getProducedRessources();
        for (Map.Entry< Ressource, Integer> entry : producedRessources.entrySet()) {
            PlayerInventory.productRessource(entry.getKey(), entry.getValue());
        }

        HashMap< Ressource, Integer> consumedRessources = WorkType.BLACKSMITH.getConsumedRessources();
        for (Map.Entry< Ressource, Integer> entry : consumedRessources.entrySet()) {
            PlayerInventory.useRessource(entry.getKey(), entry.getValue());
        }
    }
}
