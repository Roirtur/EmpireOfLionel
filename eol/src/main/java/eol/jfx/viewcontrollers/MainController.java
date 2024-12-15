package eol.jfx.viewcontrollers;

import eol.jfx.managers.GameTime;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController {

  @FXML private GridPane root;
  @FXML private Label timeLabel;

  @FXML
  public void initialize() {
    try {
      FXMLLoader mapLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/map.fxml"));
      StackPane mapPane = mapLoader.load();
      root.add(mapPane, 0, 0);
      GridPane.setHgrow(mapPane, Priority.ALWAYS);
      GridPane.setVgrow(mapPane, Priority.ALWAYS);

      FXMLLoader buildingLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/building.fxml"));
      VBox buildingPane = buildingLoader.load();
      root.add(buildingPane, 0, 1);
      GridPane.setHgrow(buildingPane, Priority.ALWAYS);
      GridPane.setVgrow(buildingPane, Priority.ALWAYS);

      FXMLLoader resourcesLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/resources.fxml"));
      HBox resourcesPane = resourcesLoader.load(); // Charger un HBox
      root.add(resourcesPane, 1, 0);
      GridPane.setHgrow(resourcesPane, Priority.ALWAYS);
      GridPane.setVgrow(resourcesPane, Priority.ALWAYS);

      FXMLLoader addResidentLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/add_resident.fxml"));
      VBox addResidentPane = addResidentLoader.load();
      root.add(addResidentPane, 1, 1);
      GridPane.setHgrow(addResidentPane, Priority.ALWAYS);
      GridPane.setVgrow(addResidentPane, Priority.ALWAYS);

      timeLabel.textProperty().bind(GameTime.getInstance().timeProperty());

    } catch (IOException e) {
    }
  }
}