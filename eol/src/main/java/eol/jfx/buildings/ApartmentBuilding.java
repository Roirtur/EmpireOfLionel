package eol.jfx.buildings;

import eol.jfx.residents.works.*;
import eol.jfx.ressources.Ressource;
import java.util.HashMap;

public class ApartmentBuilding extends Building {

    private final WorkType workertype = null;

    public ApartmentBuilding(int x, int y) {
        super(x, y, BuildingType.APARTMENT.getWidth(), BuildingType.APARTMENT.getHeight(), 60, 0, 6, new HashMap<Ressource, Integer>() {
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
