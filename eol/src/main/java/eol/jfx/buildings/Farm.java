package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.ressources.Ressource;
import eol.jfx.residents.works.*;

public class Farm extends Building {

    private final Work workertype = new Farmer();

    public Farm(int x, int y) {
        super(x, y, 3, 3, 5, 3, 2, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 5);
                put(Ressource.STONE, 5);
            }
        });
    }

    @Override
    public Work getWorkerType() {
        return workertype;
    }

}
