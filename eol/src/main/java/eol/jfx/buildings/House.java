package eol.jfx.buildings;

import eol.jfx.residents.works.*;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;

public class House extends Building {

    private final WorkType workertype = null;

    public House(int x, int y) {
        super(x, y, BuildingType.HOUSE.getWidth(), BuildingType.HOUSE.getHeight(), 4, 0, 4, new HashMap<Ressource, Integer>() {
            {
                put(Ressource.WOOD, 2);
                put(Ressource.STONE, 2);
            }
        });
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }
}
