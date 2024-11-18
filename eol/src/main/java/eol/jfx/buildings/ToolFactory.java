package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.ToolMaker;
import eol.jfx.residents.works.Work;
import eol.jfx.ressources.Ressource;

public class ToolFactory extends Building {

    private final Work workertype = new ToolMaker();

    public ToolFactory(int x, int y) {
        super(x, y, 4, 3, 0, 12, 8, new HashMap<Ressource, Integer>() {
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
