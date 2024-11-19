package eol.jfx.buildings;

import java.util.HashMap;

import eol.jfx.residents.works.WorkType;
import eol.jfx.ressources.Ressource;

public class ToolFactory extends Building {

    private final WorkType workertype = WorkType.TOOLMAKER;

    public final static BuildingType TYPE = BuildingType.TOOLFACTORY;

    public ToolFactory(int x, int y) {
        super(x, y, 4, 3, 0, 12, 8, new HashMap<Ressource, Integer>() {
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
