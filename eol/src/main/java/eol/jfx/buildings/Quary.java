package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.Miner;
import eol.jfx.residents.works.Work;
import eol.jfx.ressources.Ressource;

public class Quary extends Building {

    private final Work workertype = new Miner();

    public Quary(int x, int y) {
        super(x, y, 2, 2, 2, 30, 2, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 50);
            }
        });
    }

    @Override
    public Work getWorkerType() {
        return workertype;
    }

}
