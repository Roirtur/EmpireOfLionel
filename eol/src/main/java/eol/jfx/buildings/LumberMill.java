package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.Ressource;

public class LumberMill extends Building {

    private final WorkType workertype = WorkType.WOODWORKER;

    public final static BuildingType TYPE = BuildingType.LUMBERMILL;

    public LumberMill(int x, int y) {
        super(x, y, BuildingType.LUMBERMILL.getWidth(), BuildingType.LUMBERMILL.getHeight(), 0, 10, 4, new HashMap<Ressource, Integer>() {
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
