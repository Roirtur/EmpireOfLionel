package eol.jfx.viewcontrollers;

import eol.jfx.gamesettings.Difficulty;
import eol.jfx.gamesettings.SceneManager;
import eol.jfx.managers.GameManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class SettingsController implements Initializable {

  @FXML private ComboBox<String> difficultyComboBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    difficultyComboBox.setItems(
        FXCollections.observableArrayList("EASY", "MEDIUM", "HARD", "GODMOD"));
    difficultyComboBox.setValue("EASY"); // Set default difficulty value to EASY
  }

  @FXML
  private void handleSave() {
    String selectedDifficulty = difficultyComboBox.getValue();
    Difficulty difficulty = Difficulty.valueOf(selectedDifficulty);
    GameManager.setSelectedDifficulty(difficulty);

    // Go back to the menu
    handleBack();
  }

  @FXML
  private void handleBack() {
    SceneManager.getInstance().showScene("menu");
  }
}