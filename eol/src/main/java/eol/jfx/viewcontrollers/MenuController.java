package eol.jfx.viewcontrollers;

import eol.jfx.gamesettings.SceneManager;
import eol.jfx.managers.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

  @FXML private Button startGameButton;

  @FXML
  private void handleStartGame() {
    GameManager.startGame(); // Start the game
    SceneManager.getInstance().showScene("main");
  }

  @FXML
  private void handleSettings() {
    SceneManager.getInstance().showScene("settings");
  }

  @FXML
  private void handleExit() {
    System.exit(0);
  }
}