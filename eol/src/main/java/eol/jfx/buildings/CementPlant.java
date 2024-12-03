package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.Ressource;

public class CementPlant extends Building {

    private final WorkType workertype = WorkType.CEMENTPRODUCTOR;

    public final static BuildingType TYPE = BuildingType.CEMENTPLANT;

    public CementPlant(int x, int y) {
        super(x, y, BuildingType.CEMENTPLANT.getWidth(), BuildingType.CEMENTPLANT.getHeight(), 0, 10, 4, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 50);
                put(Ressource.STONE, 50);
            }
        });
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
