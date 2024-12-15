package eol.jfx.gamesettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneManager {

  private static SceneManager instance;
  private Stage primaryStage;
  private final Map<String, Scene> scenes = new HashMap<>();

  private SceneManager() {}

  public static SceneManager getInstance() {
    if (instance == null) {
      instance = new SceneManager();
    }
    return instance;
  }

  public void setPrimaryStage(Stage stage) { this.primaryStage = stage; }

  public void loadScene(String name, String fxmlFile) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    Parent root = loader.load();
    Screen screen = Screen.getPrimary();
    javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
    Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
    scenes.put(name, scene);
  }

  public void showScene(String name) {
    Scene scene = scenes.get(name);
    if (scene != null) {
      primaryStage.setScene(scene);
      primaryStage.show();
    } else {
      System.err.println("Scene " + name + " not found!");
    }
  }
}