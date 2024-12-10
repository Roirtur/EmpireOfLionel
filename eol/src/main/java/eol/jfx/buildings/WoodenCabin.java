package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class WoodenCabin extends Building {

    private final WorkType workertype = WorkType.LUMBERJACK;

    public final static BuildingType TYPE = BuildingType.WOODENCABIN;

    public WoodenCabin(int x, int y) {
        super(
                x,
                y,
                BuildingType.WOODENCABIN.getWidth(),
                BuildingType.WOODENCABIN.getHeight(),
                BuildingType.WOODENCABIN.getMaxResidents(),
                BuildingType.WOODENCABIN.getMaxWorkers(),
                BuildingType.WOODENCABIN.getConstructionTime(),
                BuildingType.WOODENCABIN.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }
}
