package eol.jfx.Building;

import java.util.HashMap;

import eol.jfx.ressources.Ressource;

public class Farm extends Building {

    HashMap<Ressource, Integer> constructionCost = new HashMap<Ressource, Integer>() {{
        put(Ressource.WOOD, 5);
        put(Ressource.STONE, 5);
    }};

    public Farm(int x, int y) {
        super(x, y, 3, 3, 5, 3, 2);
    }

}
