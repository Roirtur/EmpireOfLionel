package eol.jfx;

import eol.jfx.gamesettings.Difficulty;
import eol.jfx.managers.GameManager;
import eol.jfx.ressources.InventoryInitiator;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    InventoryInitiator.initializeInventory(Difficulty.GODMOD);
    FXMLLoader fxmlLoader =
        new FXMLLoader(App.class.getResource("/eol/jfx/main.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene =
        new Scene(root, 720, 480); // Set initial window size to 720x480

    stage.setTitle("Empire Of Lionel");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    GameManager.startGame();
    launch();
  }
}
