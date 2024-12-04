package eol.jfx.viewcontrollers;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuildingCreatorController {

  @FXML private HBox buildingBox;

  private final Map<String, Image> imageCache = new HashMap<>();

  @FXML
  public void initialize() {
    if (buildingBox == null) {
      System.err.println("buildingBox is null. Check fx:id in building.fxml.");
    } else {
      System.out.println("buildingBox is properly injected.");
      preloadImages();
      initializeBuildings();
    }
  }

  private void preloadImages() {
    // Preload all building images
    String[] buildingTypes = {"apartment", "cementplant", "farm",
                              "house",     "lumbermill",  "quary",
                              "steelmill", "toolfactory", "woodencabin"};
    for (String type : buildingTypes) {
      String imagePath = "/eol/img_no_bg/buildings/" + type + ".png";
      getCachedImage(imagePath);
    }
  }

  private void initializeBuildings() {
    try {
      URL buildingFolderUrl =
          getClass().getResource("/eol/img_no_bg/buildings");
      if (buildingFolderUrl == null) {
        System.err.println("Building folder not found.");
        return;
      }

      File buildingFolder = new File(buildingFolderUrl.toURI());
      File[] files =
          buildingFolder.listFiles((dir, name) -> name.endsWith(".png"));

      if (files != null) {
        for (File file : files) {
          String imagePath = "/eol/img_no_bg/buildings/" + file.getName();
          Image image = getCachedImage(imagePath);

          // Define the desired output width and height
          double outputWidth =
              50; // Example width, you can set this to any value
          double outputHeight =
              50; // Example height, you can set this to any value

          Canvas canvas = new Canvas(outputWidth, outputHeight);
          GraphicsContext gc = canvas.getGraphicsContext2D();
          gc.setImageSmoothing(false); // Disable image smoothing
          gc.drawImage(image, 0, 0, outputWidth, outputHeight);

          Button button = new Button();
          button.setGraphic(canvas);
          button.setId(file.getName().replace(".png", ""));
          button.getStyleClass().add("image-button");
          button.setOnAction(event -> setSelectedBuilding(button));

          Label nameLabel =
              new Label(file.getName().replace(".png", "").toUpperCase());

          VBox vbox = new VBox(nameLabel, button);
          vbox.setSpacing(5);
          buildingBox.getChildren().add(vbox);
        }
      } else {
        System.err.println("No image files found in the building folder.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setSelectedBuilding(Button building) {
    String buildingType = building.getId();
    if (buildingName.equals(buildingType)) {
      buildingName = "";
      building.getStyleClass().remove("building-selected");
      selectedButton = null;
    } else {
      if (selectedButton != null) {
        selectedButton.getStyleClass().remove("building-selected");
      }
      buildingName = buildingType;
      building.getStyleClass().add("building-selected");
      selectedButton = building;
    }
    System.out.println("Selected building: " + buildingName);
  }

  public static String getSelectedBuilding() { return buildingName; }

  private Image getCachedImage(String path) {
    if (!imageCache.containsKey(path)) {
      try {
        Image image =
            new Image(getClass().getResourceAsStream(path), 32, 32, false,
                      false); // Disable smoothing and set dimensions
        imageCache.put(path, image);
      } catch (Exception e) {
        System.err.println("BuildingCreatorController - Image not found: " +
                           path);
        return null;
      }
    }
    return imageCache.get(path);
  }

  private static String buildingName = "";
  private Button selectedButton = null;
}
