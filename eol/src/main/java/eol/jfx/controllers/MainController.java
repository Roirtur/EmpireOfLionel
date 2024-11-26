package eol.jfx.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class MainController {

  @FXML private GridPane root; // Utilisez GridPane au lieu de BorderPane

  @FXML
  public void initialize() {
    try {
      // Charger map.fxml
      FXMLLoader mapLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/map.fxml"));
      GridPane mapPane = mapLoader.load(); // Charger un GridPane
      root.add(mapPane, 0, 0);             // Ajoutez mapPane dans le GridPane
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}