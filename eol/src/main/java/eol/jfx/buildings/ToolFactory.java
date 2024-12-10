package eol.jfx.buildings;

import eol.jfx.residents.works.WorkType;

public class ToolFactory extends Building {

    private final WorkType workertype = WorkType.TOOLMAKER;

    public final static BuildingType TYPE = BuildingType.TOOLFACTORY;

    public ToolFactory(int x, int y) {
        super(
                x,
                y,
                BuildingType.TOOLFACTORY.getWidth(),
                BuildingType.TOOLFACTORY.getHeight(),
                BuildingType.TOOLFACTORY.getMaxResidents(),
                BuildingType.TOOLFACTORY.getMaxWorkers(),
                BuildingType.TOOLFACTORY.getConstructionTime(),
                BuildingType.TOOLFACTORY.getCost()
        );
    }

    @Override
    public WorkType getWorkerType() {
        return workertype;
    }

}
