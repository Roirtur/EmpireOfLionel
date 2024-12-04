package eol.jfx.viewcontrollers;

import eol.jfx.buildings.Building;
import eol.jfx.buildings.BuildingFactory;
import eol.jfx.buildings.BuildingType;
import eol.jfx.managers.GameManager;
import eol.jfx.managers.GridMap;
import eol.jfx.residents.Resident;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class MapController {

  @FXML private GridPane mapGrid;

  private final Map<String, Image> imageCache = new HashMap<>();
  private final Map<String, Canvas> canvasCache = new HashMap<>();
  private Popup currentPopup;

  @FXML
  public void initialize() {
    if (mapGrid == null) {
      System.err.println("mapGrid is null. Check fx:id in map.fxml.");
    } else {
      System.out.println("mapGrid is properly injected.");
      preloadImages();
      initializeGrid();
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
    // Preload ground image
    getCachedImage("/eol/img_no_bg/ground.png");
  }

  public void putBuildingOnMap(int row, int col) {
    String buildingType = BuildingCreatorController.getSelectedBuilding();
    if (buildingType == null || buildingType.equals("")) {
      System.out.println("No building selected.");
      return;
    }

    System.out.println(buildingType.toUpperCase());
    BuildingType type = BuildingType.valueOf(buildingType.toUpperCase());
    Building building = BuildingFactory.createBuilding(type, col, row);

    if (GridMap.placeBuilding(building, col, row)) {
      GameManager.getInstance().addBuilding(building);
      updateMapDisplay(col, row, building.getWidth(), building.getHeight(),
                       building);
    } else {
      System.out.println("Can't place building at " + row + ", " + col);
    }
  }

  public void removeBuildingFromMap(int row, int col) {
    Building building = GameManager.getInstance().findBuilding(col, row);
    if (building == null) {
      System.out.println("No building found at " + row + ", " + col);
      return;
    }

    GameManager.getInstance().removeBuilding(building);

    updateMapDisplay(col, row, building.getWidth(), building.getHeight(), null);

    System.out.println("Building removed at " + row + ", " + col);
  }

  private void initializeGrid() {
    int rows = GridMap.getHeight();
    int cols = GridMap.getWidth();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Image ground = getCachedImage("/eol/img_no_bg/ground.png");

        // Define the desired output width and height
        double outputWidth = 50; // Example width, you can set this to any value
        double outputHeight =
            50; // Example height, you can set this to any value

        Canvas canvas = new Canvas(outputWidth, outputHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false); // Disable image smoothing
        gc.drawImage(ground, 0, 0, outputWidth, outputHeight);

        Button button = new Button();
        button.setGraphic(canvas);
        button.getStyleClass().add("image-button");
        final int finalRow = row;
        final int finalCol = col;
        button.setOnAction(event -> putBuildingOnMap(finalRow, finalCol));

        mapGrid.add(button, col, row);
        canvasCache.put(row + "," + col, canvas);
      }
    }
  }

  private void updateMapDisplay(int startX, int startY, int width, int height,
                                Building building) {
    boolean[][] map = GridMap.getGrid();

    for (int row = startY; row < startY + height; row++) {
      for (int col = startX; col < startX + width; col++) {
        if (map[row][col]) {
          String imagePath;
          if (building == null) {
            imagePath = "/eol/img_no_bg/ground.png";
          } else {
            String buildingType =
                building.getClass().getSimpleName().toLowerCase();
            imagePath = "/eol/img_no_bg/buildings/" + buildingType + ".png";
          }
          Image buildingImage = getCachedImage(imagePath);

          if (buildingImage != null) {
            int widthInPixels = width * 50;
            int heightInPixels = height * 50;

            // Remove existing canvases in the area
            for (int r = startY; r < startY + height; r++) {
              for (int c = startX; c < startX + width; c++) {
                Canvas existingCanvas = canvasCache.remove(r + "," + c);
                if (existingCanvas != null) {
                  mapGrid.getChildren().remove(existingCanvas);
                }
              }
            }

            // Create a new canvas that spans multiple tiles
            Canvas canvas = new Canvas(widthInPixels, heightInPixels);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setImageSmoothing(false); // Disable image smoothing
            gc.drawImage(buildingImage, 0, 0, widthInPixels, heightInPixels);

            Button button = new Button();
            button.setGraphic(canvas);
            button.getStyleClass().add("image-button");

            if (building != null) {
              button.setOnAction(
                  event -> showBuildingPopup(building, button, widthInPixels));
            }

            mapGrid.add(button, startX, startY, width, height);
            canvasCache.put(startY + "," + startX, canvas);
          }
        }
      }
    }
  }

  private void showBuildingPopup(Building building, Button sourceButton,
                                 int widthInPixels) {
    if (currentPopup != null) {
      currentPopup.hide();
    }

    Popup popup = new Popup();
    currentPopup = popup;

    VBox popupContent = new VBox();
    popupContent.setStyle("-fx-background-color: white; -fx-padding: 10; "
                          + "-fx-border-color: black; -fx-border-width: 1;");

    Label buildingInfo = new Label(
        "Building: " + building.getClass().getSimpleName() + "\n"
        + "Position: (" + building.getX() + ", " + building.getY() + ")\n"
        + "Size: " + building.getWidth() + "x" + building.getHeight());

    Button addWorkerButton = new Button("Add Worker");
    addWorkerButton.setOnAction(e -> {
      try {
        GameManager.assignWorkerToBuilding(building);
        System.out.println("Worker added to building: " +
                           building.getClass().getSimpleName());
      } catch (IllegalStateException ex) {
        System.out.println(ex.getMessage());
      }
      popup.hide();
    });

    Button removeWorkerButton = new Button("Remove Worker");
    removeWorkerButton.setOnAction(e -> {
      try {
        GameManager.removeWorkerFromBuilding(building);
        System.out.println("Worker removed from building: " +
                           building.getClass().getSimpleName());
      } catch (IllegalStateException ex) {
        System.out.println(ex.getMessage());
      }
      popup.hide();
    });

    Button removeBuildingButton = new Button("Remove Building");
    removeBuildingButton.setOnAction(e -> {
      try {
        removeBuildingFromMap(building.getY(), building.getX());
      } catch (IllegalStateException ex) {
        System.out.println(ex.getMessage());
      }
      popup.hide();
    });

    Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> popup.hide());

    popupContent.getChildren().addAll(buildingInfo, addWorkerButton,
                                      removeWorkerButton, removeBuildingButton,
                                      closeButton);
    popup.getContent().add(popupContent);

    // Calculate the position to show the popup next to the clicked button
    Point2D buttonPosition = sourceButton.localToScene(0.0, 0.0);
    double x = buttonPosition.getX() +
               sourceButton.getScene().getWindow().getX() +
               sourceButton.getScene().getX() + widthInPixels;
    double y = buttonPosition.getY() +
               sourceButton.getScene().getWindow().getY() +
               sourceButton.getScene().getY();

    popup.show(sourceButton.getScene().getWindow(), x, y);

    // Close the popup if clicking elsewhere
    Scene scene = sourceButton.getScene();
    scene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
      if (!popup.getContent().contains((Node)event.getTarget())) {
        popup.hide();
      }
    });
  }

  private Image getCachedImage(String path) {
    if (!imageCache.containsKey(path)) {
      try {
        Image image =
            new Image(getClass().getResourceAsStream(path), 32, 32, false,
                      false); // Disable smoothing and set dimensions
        imageCache.put(path, image);
      } catch (Exception e) {
        System.err.println("Image not found: " + path);
        return null;
      }
    }
    return imageCache.get(path);
  }
}