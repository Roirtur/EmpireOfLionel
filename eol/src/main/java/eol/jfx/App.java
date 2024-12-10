package eol.jfx;

import eol.jfx.gamesettings.SceneManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    SceneManager sceneManager = SceneManager.getInstance();
    sceneManager.setPrimaryStage(stage);

    sceneManager.loadScene("menu", "/eol/jfx/menu.fxml");
    sceneManager.loadScene("main", "/eol/jfx/main.fxml");
    sceneManager.loadScene("settings", "/eol/jfx/settings.fxml");
    sceneManager.loadScene("victory", "/eol/jfx/victory.fxml");
    sceneManager.loadScene("defeat", "/eol/jfx/defeat.fxml");

    stage.setTitle("Empire Of Lionel");
    sceneManager.showScene("menu");
  }

  public static void main(String[] args) {
    launch();
    System.exit(0);
  }
}
