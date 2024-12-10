package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class Quary extends Building {

    private final WorkType workertype = WorkType.MINER;

    public final static BuildingType TYPE = BuildingType.QUARY;

    public Quary(int x, int y) {
        super(
                x,
                y,
                BuildingType.QUARY.getWidth(),
                BuildingType.QUARY.getHeight(),
                BuildingType.QUARY.getMaxResidents(),
                BuildingType.QUARY.getMaxWorkers(),
                BuildingType.QUARY.getConstructionTime(),
                BuildingType.QUARY.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
