package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class SteelMill extends Building {

    private final WorkType workertype = WorkType.BLACKSMITH;

    public final static BuildingType TYPE = BuildingType.STEELMILL;

    public SteelMill(int x, int y) {
        super(
                x,
                y,
                BuildingType.STEELMILL.getWidth(),
                BuildingType.STEELMILL.getHeight(),
                BuildingType.STEELMILL.getMaxResidents(),
                BuildingType.STEELMILL.getMaxWorkers(),
                BuildingType.STEELMILL.getConstructionTime(),
                BuildingType.STEELMILL.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
