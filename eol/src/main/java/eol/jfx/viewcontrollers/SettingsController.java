package eol.jfx.viewcontrollers;

import eol.jfx.gamesettings.Difficulty;
import eol.jfx.ressources.InventoryInitiator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

  @FXML private ComboBox<String> difficultyComboBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    difficultyComboBox.setItems(
        FXCollections.observableArrayList("EASY", "MEDIUM", "HARD", "GODMOD"));
    difficultyComboBox.setValue("EASY"); // Set default value to EASY
  }

  @FXML
  private void handleSave() {
    String selectedDifficulty = difficultyComboBox.getValue();
    Difficulty difficulty = Difficulty.valueOf(selectedDifficulty);
    InventoryInitiator.initializeInventory(difficulty);

    // Go back to the menu
    handleBack();
  }

  @FXML
  private void handleBack() {
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/menu.fxml"));
      Parent root = fxmlLoader.load();
      Scene scene =
          new Scene(root, 720, 480); // Set initial window size to 720x480

      // Get the current stage from the difficultyComboBox
      Stage stage = (Stage)difficultyComboBox.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}