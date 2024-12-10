package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class House extends Building {

    private final WorkType workertype = null;

    public House(int x, int y) {
        super(
                x,
                y,
                BuildingType.HOUSE.getWidth(),
                BuildingType.HOUSE.getHeight(),
                BuildingType.HOUSE.getMaxResidents(),
                BuildingType.HOUSE.getMaxWorkers(),
                BuildingType.HOUSE.getConstructionTime(),
                BuildingType.HOUSE.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }
}
