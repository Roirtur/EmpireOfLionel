package eol.jfx.viewcontrollers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController {

  @FXML private GridPane root; // Utilisez GridPane au lieu de BorderPane

  @FXML
  public void initialize() {
    try {
      // Charger map.fxml
      FXMLLoader mapLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/map.fxml"));
      StackPane mapPane = mapLoader.load(); // Charger un StackPane
      root.add(mapPane, 0, 0);              // Ajoutez mapPane dans le GridPane
      GridPane.setHgrow(mapPane, Priority.ALWAYS);
      GridPane.setVgrow(mapPane, Priority.ALWAYS);

      // Charger building.fxml
      FXMLLoader buildingLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/building.fxml"));
      VBox buildingPane = buildingLoader.load(); // Charger un VBox
      root.add(buildingPane, 0, 1); // Ajoutez buildingPane dans le GridPane
      GridPane.setHgrow(buildingPane, Priority.ALWAYS);
      GridPane.setVgrow(buildingPane, Priority.ALWAYS);

      // Charger resources.fxml
      FXMLLoader resourcesLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/resources.fxml"));
      HBox resourcesPane = resourcesLoader.load(); // Charger un HBox
      root.add(resourcesPane, 1, 0); // Ajoutez resourcesPane dans le GridPane
      GridPane.setHgrow(resourcesPane, Priority.ALWAYS);
      GridPane.setVgrow(resourcesPane, Priority.ALWAYS);

      // Charger add_resident.fxml
      FXMLLoader addResidentLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/add_resident.fxml"));
      VBox addResidentPane = addResidentLoader.load(); // Charger un VBox
      root.add(addResidentPane, 1,
               1); // Ajoutez addResidentPane dans le GridPane
      GridPane.setHgrow(addResidentPane, Priority.ALWAYS);
      GridPane.setVgrow(addResidentPane, Priority.ALWAYS);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}