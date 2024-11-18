package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.CementProductor;
import eol.jfx.residents.works.Work;
import eol.jfx.ressources.Ressource;

public class CementPlant extends Building {

    private final Work workertype = new CementProductor();

    public CementPlant(int x, int y) {
        super(x, y, 4, 3, 0, 10, 4, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 50);
                put(Ressource.STONE, 50);
            }
        });
    }

    @Override
    public Work getWorkerType() {
        return workertype;
    }

}
