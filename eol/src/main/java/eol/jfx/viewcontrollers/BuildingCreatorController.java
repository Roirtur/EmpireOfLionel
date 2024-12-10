package eol.jfx.viewcontrollers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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

          // Add tooltip to button
          Tooltip tooltip = createTooltip(button.getId());
          button.setTooltip(
              tooltip); // Use setTooltip instead of Tooltip.install

          // Add mouse event listeners to adjust tooltip position
          button.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            tooltip.setX(event.getScreenX() + 10); // Adjust X position
            tooltip.setY(event.getScreenY() + 10); // Adjust Y position
          });

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

  private Tooltip createTooltip(String buildingType) {
    Tooltip tooltip = new Tooltip();
    VBox vbox = new VBox();
    vbox.setSpacing(10); // Increase spacing for larger elements

    // Example construction costs, replace with actual costs
    HashMap<String, Integer> constructionCost =
        getConstructionCost(buildingType);

    for (Map.Entry<String, Integer> entry : constructionCost.entrySet()) {
      HBox hbox = new HBox();
      hbox.setSpacing(10); // Increase spacing for larger elements

      // Define larger dimensions for the image and background
      double imageSize = 40; // Example size, you can set this to any value

      ImageView imageView = new ImageView(getResourceImage(entry.getKey()));
      imageView.setFitWidth(imageSize);
      imageView.setFitHeight(imageSize);

      // Add a white background behind the image
      Rectangle background = new Rectangle(imageSize, imageSize);
      background.setFill(Color.WHITE);

      StackPane stackPane = new StackPane();
      stackPane.getChildren().addAll(background, imageView);

      Label quantityLabel = new Label(entry.getValue().toString());
      quantityLabel.setStyle(
          "-fx-font-size: 16px;"); // Increase font size for better visibility

      hbox.getChildren().addAll(stackPane, quantityLabel);
      vbox.getChildren().add(hbox);
    }

    tooltip.setGraphic(vbox);
    tooltip.setShowDelay(
        Duration.ZERO); // Set show delay to zero using javafx.util.Duration
    return tooltip;
  }

  private HashMap<String, Integer> getConstructionCost(String buildingType) {
    HashMap<String, Integer> constructionCost = new HashMap<>();
    switch (buildingType) {
    case "house":
      constructionCost.put("Wood", 2);
      constructionCost.put("Stone", 2);
      break;
    case "lumbermill":
      constructionCost.put("Wood", 50);
      constructionCost.put("Stone", 50);
      break;
    case "quary":
      constructionCost.put("Wood", 50);
      break;
    case "steelmill":
      constructionCost.put("Wood", 100);
      constructionCost.put("Stone", 50);
      break;
    case "toolfactory":
      constructionCost.put("Wood", 50);
      constructionCost.put("Stone", 50);
      break;
    case "woodencabin":
      constructionCost.put("Wood", 1);
      break;
    case "farm":
      constructionCost.put("Wood", 5);
      constructionCost.put("Stone", 5);
      break;
    case "cementplant":
      constructionCost.put("Wood", 50);
      constructionCost.put("Stone", 50);
      break;
    case "apartment":
      constructionCost.put("Wood", 50);
      constructionCost.put("Stone", 50);
      break;
    default:
      System.err.println("Building type not found: " + buildingType);
    }
    return constructionCost;
  }

  private Image getResourceImage(String resourceName) {
    String imagePath =
        "/eol/img_no_bg/resources/" + resourceName.toLowerCase() + ".png";
    return new Image(getClass().getResourceAsStream(imagePath));
  }

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
