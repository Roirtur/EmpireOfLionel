package eol.jfx.viewcontrollers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import eol.jfx.buildings.BuildingType;
import eol.jfx.ressources.Ressource;
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

          double outputWidth = 50;
          double outputHeight = 50;

          Canvas canvas = new Canvas(outputWidth, outputHeight);
          GraphicsContext gc = canvas.getGraphicsContext2D();
          gc.setImageSmoothing(false);
          gc.drawImage(image, 0, 0, outputWidth, outputHeight);

          Button button = new Button();
          button.setGraphic(canvas);
          button.setId(file.getName().replace(".png", ""));
          button.getStyleClass().add("image-button");

          button.setOnAction(event -> setSelectedBuilding(button));

          Tooltip tooltip = createTooltip(button.getId());
          button.setTooltip(tooltip);

          button.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            tooltip.setX(event.getScreenX() + 10);
            tooltip.setY(event.getScreenY() + 10);
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
    } catch (URISyntaxException e) {
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
    vbox.setSpacing(10);

    Map<Ressource, Integer> constructionCost =
        getConstructionCost(buildingType);

    for (Map.Entry<Ressource, Integer> entry : constructionCost.entrySet()) {
      HBox hbox = new HBox();
      hbox.setSpacing(10);

      double imageSize = 40;

      ImageView imageView =
          new ImageView(getResourceImage(entry.getKey().name()));
      imageView.setFitWidth(imageSize);
      imageView.setFitHeight(imageSize);

      Rectangle background = new Rectangle(imageSize, imageSize);
      background.setFill(Color.WHITE);

      StackPane stackPane = new StackPane();
      stackPane.getChildren().addAll(background, imageView);

      Label quantityLabel = new Label(entry.getValue().toString());
      quantityLabel.setStyle("-fx-font-size: 16px;");

      hbox.getChildren().addAll(stackPane, quantityLabel);
      vbox.getChildren().add(hbox);
    }

    tooltip.setGraphic(vbox);
    tooltip.setShowDelay(Duration.ZERO);
    return tooltip;
  }

  private HashMap<Ressource, Integer> getConstructionCost(String buildingType) {
    try {
      BuildingType type = BuildingType.valueOf(buildingType.toUpperCase());
      return type.getCost();
    } catch (IllegalArgumentException e) {
      System.err.println("Building type not found: " + buildingType);
      return new HashMap<>();
    }
  }

  private Image getResourceImage(String resourceName) {
    String imagePath =
        "/eol/img_no_bg/resources/" + resourceName.toLowerCase() + ".png";
    return new Image(getClass().getResourceAsStream(imagePath));
  }

  private Image getCachedImage(String path) {
    if (!imageCache.containsKey(path)) {
      try {
        Image image = new Image(getClass().getResourceAsStream(path), 32, 32,
                                false, false);
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
