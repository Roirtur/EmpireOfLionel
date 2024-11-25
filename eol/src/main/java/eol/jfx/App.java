package eol.jfx;

import java.io.IOException;

import eol.jfx.buildings.BuildingType;
import eol.jfx.gamesettings.Difficulty;
import eol.jfx.managers.GameManager;
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
        scene = new Scene(loadFXML("menu"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    // static void setRoot(String fxml) throws IOException {
    //     scene.setRoot(loadFXML(fxml));
    // }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader
                = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        // launch();

        // Temporary code to test
        InventoryInitiator.initializeInventory(Difficulty.GODMOD);

        PlayerInventory.print();

        // Create a new Farm
        // Building farm = new Farm(0, 0);
        // Building woodcabbin = new LumberMill(6, 6);
        // farm.printBuilding();
        // Building house = new House(3, 3);
        // house.printBuilding();
        // new Thread(() -> {
        //   while (!farm.isBuilt) {
        //     try {
        //       System.out.println("Waiting for the farm to be built...");
        //       Thread.sleep(500);
        //     } catch (InterruptedException e) {
        //       System.err.println("Thread was interrupted!");
        //     }
        //   }
        //   while (!house.isBuilt) {
        //     try {
        //       System.out.println("Waiting for the house to be built...");
        //       Thread.sleep(500);
        //     } catch (InterruptedException e) {
        //       System.err.println("Thread was interrupted!");
        //     }
        //   }
        //   while (!woodcabbin.isBuilt) {
        //     try {
        //       System.out.println("Waiting for the lumber to be built...");
        //       Thread.sleep(500);
        //     } catch (InterruptedException e) {
        //       System.err.println("Thread was interrupted!");
        //     }
        //   }
        //   // Create a new Farmer
        //   Resident farmer = new Resident(0, 0);
        //   Resident woodcutter = new Resident(0, 0);
        //   // Assign the farmer to the farm
        //   farmer.giveTool();
        //   farmer.setWorkplace(farm);
        //   farmer.setHouse(house);
        //   farmer.print();
        //   woodcutter.giveTool();
        //   woodcutter.setWorkplace(woodcabbin);
        //   woodcutter.setHouse(house);
        //   PlayerInventory.print();
        //   woodcutter.update();
        //   woodcutter.print();
        //   Map.printGrid();
        //   PlayerInventory.print();
        // }).start();
        // GameManager.addBuilding(BuildingType.APARTMENT, 3, 3);
        // GameManager.addBuilding(BuildingType.APARTMENT, 3, 1);
        // GameManager.addBuilding(BuildingType.APARTMENT, 3, 5);
        // GameManager.addBuilding(BuildingType.APARTMENT, 3, 7);
        // GameManager.addBuilding(BuildingType.APARTMENT, 0, 3);
        // GameManager.addBuilding(BuildingType.APARTMENT, 6, 3);
        // Map.printGrid();
        // GameManager.removeBuilding(4, 4);
        // Map.printGrid();
        // GameTime.getInstance().startTimer();
        // new Thread(() -> {
        //   try {
        //     Thread.sleep(100000);
        //     GameTime.getInstance().stopTimer();
        //   } catch (InterruptedException e) {
        //     System.err.println("Thread was interrupted!");
        //   }
        // }).start();
        GameManager.startGame();
        GameManager.addResident();
        GameManager.addBuilding(BuildingType.FARM, 3, 3);
        // Wait for buildings to be built
        while (!GameManager.getBuildings().get(0).isBuilt) {
            try {
                System.out.println("Waiting for the farm to be built...");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted!");
            }
        }
        GameManager.assignWorkerToBuilding(GameManager.getBuildings().get(0));
        // Wait 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted!");
        }

        GameManager.removeWorkerFromBuilding(GameManager.getBuildings().get(0));
    }
}
