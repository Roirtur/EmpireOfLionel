package eol.jfx.buildings;

public class BuildingFactory {

    private static BuildingFactory instance;

    private BuildingFactory() {
        // private constructor to prevent instantiation
    }

    public static BuildingFactory getInstance() {
        if (instance == null) {
            instance = new BuildingFactory();
        }
        return instance;
    }

    public static Building createBuilding(BuildingType type, int x, int y) {
        switch (type) {
            case APARTMENT:
                return new ApartmentBuilding(x, y);
            case CEMENTPLANT:
                return new CementPlant(x, y);
            case FARM:
                return new Farm(x, y);
            case HOUSE:
                return new House(x, y);
            case LUMBERMILL:
                return new LumberMill(x, y);
            case QUARY:
                return new Quary(x, y);
            case STEELMILL:
                return new SteelMill(x, y);
            case TOOLFACTORY:
                return new ToolFactory(x, y);
            case WOODENCABIN:
                return new WoodenCabin(x, y);
            default:
                return null;
        }
    }
}
