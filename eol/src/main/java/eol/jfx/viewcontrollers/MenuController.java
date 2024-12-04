package eol.jfx.viewcontrollers;

import eol.jfx.managers.GameManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

  @FXML private Button startGameButton;

  @FXML
  private void handleStartGame() {
    try {
      GameManager.startGame(); // Start the game

      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/main.fxml"));
      Parent root = fxmlLoader.load();
      Scene scene =
          new Scene(root, 720, 480); // Set initial window size to 720x480

      // Get the current stage from the startGameButton
      Stage stage = (Stage)startGameButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleSettings() {
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/eol/jfx/settings.fxml"));
      Parent root = fxmlLoader.load();
      Scene scene =
          new Scene(root, 720, 480); // Set initial window size to 720x480

      // Get the current stage from the startGameButton
      Stage stage = (Stage)startGameButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleExit() {
    System.exit(0);
  }
}