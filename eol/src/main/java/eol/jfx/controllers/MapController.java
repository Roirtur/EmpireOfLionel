package eol.jfx.controllers;

import eol.jfx.managers.Map;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MapController {

  @FXML private GridPane mapGrid;

  @FXML
  public void initialize() {
    if (mapGrid == null) {
      System.err.println("mapGrid is null. Check fx:id in map.fxml.");
    } else {
      System.out.println("mapGrid is properly injected.");
      initializeGrid();
    }
  }

  private void initializeGrid() {
    // Assuming Map has a method to get the grid size
    int rows = Map.getWidth();
    int cols = Map.getHeight();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Image ground = new Image(getClass().getResourceAsStream(
            "/eol/img_no_bg/house-removebg-preview.png"));
        ImageView imageView = new ImageView(ground);

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);

        mapGrid.add(imageView, col, row);
      }
    }
  }
}