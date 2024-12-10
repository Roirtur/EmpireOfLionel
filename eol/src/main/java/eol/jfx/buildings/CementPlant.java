package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class CementPlant extends Building {

    private final WorkType workertype = WorkType.CEMENTPRODUCTOR;

    public final static BuildingType TYPE = BuildingType.CEMENTPLANT;

    public CementPlant(int x, int y) {
        super(
                x,
                y,
                BuildingType.CEMENTPLANT.getWidth(),
                BuildingType.CEMENTPLANT.getHeight(),
                BuildingType.CEMENTPLANT.getMaxResidents(),
                BuildingType.CEMENTPLANT.getMaxWorkers(),
                BuildingType.CEMENTPLANT.getConstructionTime(),
                BuildingType.CEMENTPLANT.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
