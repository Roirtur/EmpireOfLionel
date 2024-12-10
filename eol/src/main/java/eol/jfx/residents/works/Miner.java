package eol.jfx.residents.works;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println("Mining resources...");

        new Thread(() -> {
            try {
                generateRessources();

                Thread.sleep(WorkType.MINER.getProductionTime());
            } catch (InterruptedException e) {
            } finally {
                isWorking = false;
            }
        }).start();
    }

    @Override
    public void generateRessources() {
        HashMap< Ressource, Integer> producedRessources = WorkType.MINER.getProducedRessources();
        for (Map.Entry< Ressource, Integer> entry : producedRessources.entrySet()) {
            PlayerInventory.productRessource(entry.getKey(), entry.getValue());
        }

        HashMap< Ressource, Integer> consumedRessources = WorkType.MINER.getConsumedRessources();
        for (Map.Entry< Ressource, Integer> entry : consumedRessources.entrySet()) {
            PlayerInventory.useRessource(entry.getKey(), entry.getValue());
        }
    }
}
