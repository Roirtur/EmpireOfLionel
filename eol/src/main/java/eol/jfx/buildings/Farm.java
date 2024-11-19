package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.Ressource;

public class Farm extends Building {

    private final WorkType workertype = WorkType.FARMER;

    public final static BuildingType TYPE = BuildingType.FARM;

    public Farm(int x, int y) {
        super(x, y, 3, 3, 5, 3, 2, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 5);
                put(Ressource.STONE, 5);
            }
        });
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
