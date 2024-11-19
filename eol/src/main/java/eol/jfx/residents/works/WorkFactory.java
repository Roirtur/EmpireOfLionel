package eol.jfx.residents.works;

public class WorkFactory {

    private static WorkFactory instance;

    private WorkFactory() {
        // private constructor to prevent instantiation
    }

    public static WorkFactory getInstance() {
        if (instance == null) {
            instance = new WorkFactory();
        }
        return instance;
    }

    public static Work createWork(WorkType type) {
        switch (type) {
            case BLACKSMITH:
                return new Blacksmith();
            case CEMENTPRODUCTOR:
                return new CementProductor();
            case FARMER:
                return new Farmer();
            case LUMBERJACK:
                return new Lumberjack();
            case MINER:
                return new Miner();
            case TOOLMAKER:
                return new ToolMaker();
            case UNEMPLOYED:
                return new Unemployed();
            case WOODWORKER:
                return new Woodworker();

            default:
                return null;
        }
    }
}
