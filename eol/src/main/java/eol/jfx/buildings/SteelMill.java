package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.Blacksmith;
import eol.jfx.residents.works.Work;
import eol.jfx.ressources.Ressource;

public class SteelMill extends Building {

    private final Work workertype = new Blacksmith();

    public SteelMill(int x, int y) {
        super(x, y, 4, 3, 0, 40, 6, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 100);
                put(Ressource.STONE, 50);
            }
        });
    }

    @Override
    public Work getWorkerType() {
        return workertype;
    }

}
