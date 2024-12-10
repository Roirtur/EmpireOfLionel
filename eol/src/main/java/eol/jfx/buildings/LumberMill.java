package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class LumberMill extends Building {

    private final WorkType workertype = WorkType.WOODWORKER;

    public final static BuildingType TYPE = BuildingType.LUMBERMILL;

    public LumberMill(int x, int y) {
        super(
                x,
                y,
                BuildingType.LUMBERMILL.getWidth(),
                BuildingType.LUMBERMILL.getHeight(),
                BuildingType.LUMBERMILL.getMaxResidents(),
                BuildingType.LUMBERMILL.getMaxWorkers(),
                BuildingType.LUMBERMILL.getConstructionTime(),
                BuildingType.LUMBERMILL.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
