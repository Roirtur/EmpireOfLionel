package eol.jfx.viewcontrollers;

import eol.jfx.managers.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MapController {

    @FXML
    private GridPane mapGrid;

    @FXML
    public void initialize() {
        if (mapGrid == null) {
            System.err.println("mapGrid is null. Check fx:id in map.fxml.");
        } else {
            System.out.println("mapGrid is properly injected.");
            initializeGrid();
        }
    }

    public void putBuildingOnMap(int row, int col) {
        // Assuming Map has a method to put a building on a cell
        // Map.putBuilding(row, col);
        String building_type = BuildingCreatorController.getSelectedBuilding();
        System.out.println("Trying to place building " + building_type + " at " + row + ", " + col);

        if (building_type == null || building_type.equals("")) {
            System.out.println("No building selected.");
            return;
        }

        System.out.println("Placing building " + building_type + " at " + row + ", " + col);

    }

    private void initializeGrid() {
        // Assuming Map has a method to get the grid size
        int rows = Map.getWidth();
        int cols = Map.getHeight();
        boolean[][] map = Map.getGrid();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!map[col][row]) {
                    Image ground = new Image(
                            getClass().getResourceAsStream("/eol/img_no_bg/ground.png"));
                    ImageView imageView = new ImageView(ground);

                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(false);

                    Button button = new Button();
                    button.setGraphic(imageView);
                    button.getStyleClass().add("image-button");
                    final int finalRow = row;
                    final int finalCol = col;
                    button.setOnAction(event -> putBuildingOnMap(finalRow, finalCol));

                    mapGrid.add(button, row, col);
                } else {
                    Image ground = new Image(
                            getClass().getResourceAsStream("/eol/img_no_bg/exit.png"));
                    ImageView imageView = new ImageView(ground);

                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(false);

                    Button button = new Button();
                    button.setGraphic(imageView);
                    button.getStyleClass().add("image-button");
                    final int finalRow = row;
                    final int finalCol = col;
                    button.setOnAction(event -> {
                        System.out.println("Clicked on cell " + finalRow + ", " + finalCol);
                        putBuildingOnMap(finalRow, finalCol);
                    });

                    mapGrid.add(button, row, col);
                }
            }
        }
    }
}
