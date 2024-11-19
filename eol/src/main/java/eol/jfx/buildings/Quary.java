package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.Ressource;

public class Quary extends Building {

    private final WorkType workertype = WorkType.MINER;

    public final static BuildingType TYPE = BuildingType.QUARY;

    public Quary(int x, int y) {
        super(x, y, 2, 2, 2, 30, 2, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 50);
            }
        });
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
