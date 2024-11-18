package eol.jfx;

import java.io.IOException;

import eol.jfx.buildings.Building;
import eol.jfx.buildings.Farm;
import eol.jfx.buildings.House;
import eol.jfx.gamesettings.Difficulty;
import eol.jfx.residents.Resident;
import eol.jfx.ressources.InventoryInitiator;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
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
        scene = new Scene(loadFXML("menu"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    // static void setRoot(String fxml) throws IOException {
    //     scene.setRoot(loadFXML(fxml));
    // }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void initiateInventory() {
        InventoryInitiator.initializeInventory(Difficulty.EASY);

        for (Ressource ressource : Ressource.values()) {
            System.out.println(ressource + ": " + PlayerInventory.getRessourceQuantity(ressource));
        }
    }

    public static void main(String[] args) {
        // launch();

        // Temporary code to test
        initiateInventory();

        // Create a new Farmer
        Resident farmer = new Resident(0,0);
        // Create a new Farm
        Building farm = new Farm(0,0);
        farm.printBuilding();
        Building house = new House(0,0);
        house.printBuilding();

        new Thread(() -> {
            while(!farm.isBuilt) {
                try {
                    System.out.println("Waiting for the farm to be built...");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted!");
                }
            }
            // Assign the farmer to the farm
            farmer.giveTool();
            farmer.setWorkplace(farm);

            while(!house.isBuilt) {
                try {
                    System.out.println("Waiting for the house to be built...");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted!");
                }
            }
            farmer.setHouse(house);
            farmer.print();
        }).start();
    }

}
