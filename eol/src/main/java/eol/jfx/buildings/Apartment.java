package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class Apartment extends Building {

    private final WorkType workertype = null;

    public Apartment(int x, int y) {
        super(
                x,
                y,
                BuildingType.APARTMENT.getWidth(),
                BuildingType.APARTMENT.getHeight(),
                BuildingType.APARTMENT.getMaxResidents(),
                BuildingType.APARTMENT.getMaxWorkers(),
                BuildingType.APARTMENT.getConstructionTime(),
                BuildingType.APARTMENT.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }
}
