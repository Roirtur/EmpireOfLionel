package eol.jfx.buildings;

public enum BuildingType {
    APARTMENT(4, 4),
    CEMENTPLANT(3, 3),
    FARM(2, 2),
    HOUSE(1, 1),
    LUMBERMILL(3, 3),
    QUARY(3, 3),
    STEELMILL(3, 3),
    TOOLFACTORY(2, 2),
    WOODENCABIN(1, 1);

    private final int width;
    private final int height;

    BuildingType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
