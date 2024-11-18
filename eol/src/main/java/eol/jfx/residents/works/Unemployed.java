package eol.jfx.residents.works;

public class Unemployed extends Work {

    @Override
    public String toString() {
        return "Unemployed";
    }

    @Override
    public void work() {
        // TODO Roam around the city
    }

    @Override
    public void generateRessources() {
        // No income
    }
}
