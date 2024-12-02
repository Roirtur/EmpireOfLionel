package eol.jfx;

import java.io.IOException;

import eol.jfx.gamesettings.Difficulty;
import eol.jfx.ressources.InventoryInitiator;
import eol.jfx.ressources.PlayerInventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    InventoryInitiator.initializeInventory(Difficulty.GODMOD);
    PlayerInventory.print();

    scene = new Scene(loadFXML("main"), 1200, 800);
    stage.setScene(scene);
    stage.show();
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(App.class.getResource("/eol/jfx/" + fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) { launch(); }
}