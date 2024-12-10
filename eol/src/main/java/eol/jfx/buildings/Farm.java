package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class Farm extends Building {

    private final WorkType workertype = WorkType.FARMER;

    public final static BuildingType TYPE = BuildingType.FARM;

    public Farm(int x, int y) {
        super(
                x,
                y,
                BuildingType.FARM.getWidth(),
                BuildingType.FARM.getHeight(),
                BuildingType.FARM.getMaxResidents(),
                BuildingType.FARM.getMaxWorkers(),
                BuildingType.FARM.getConstructionTime(),
                BuildingType.FARM.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
