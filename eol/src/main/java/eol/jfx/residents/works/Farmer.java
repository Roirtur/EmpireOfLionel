package eol.jfx.residents.works;

import java.util.HashMap;
import java.util.Map;

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
        // TODO Farm at the fields;,,,

        // When the work is done, generate income
        new Thread(() -> {
            try {
                Thread.sleep(WorkType.FARMER.getProductionTime());
                generateRessources();
                System.out.println("Farmer has finished working");
                isWorking = false;
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted!");
            }
        }).start();
    }

    @Override
    public void generateRessources() {
        HashMap< Ressource, Integer> producedRessources = WorkType.FARMER.getProducedRessources();
        for (Map.Entry< Ressource, Integer> entry : producedRessources.entrySet()) {
            PlayerInventory.productRessource(entry.getKey(), entry.getValue());
        }

        HashMap< Ressource, Integer> consumedRessources = WorkType.FARMER.getConsumedRessources();
        for (Map.Entry< Ressource, Integer> entry : consumedRessources.entrySet()) {
            PlayerInventory.useRessource(entry.getKey(), entry.getValue());
        }
    }
}
