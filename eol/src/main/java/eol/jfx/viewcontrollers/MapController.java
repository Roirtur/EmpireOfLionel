package eol.jfx.viewcontrollers;

import java.util.HashMap;
import java.util.Map;

import eol.jfx.buildings.Building;
import eol.jfx.buildings.BuildingFactory;
import eol.jfx.buildings.BuildingType;
import eol.jfx.managers.GameManager;
import eol.jfx.managers.GridMap;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class MapController {

    @FXML
    private GridPane mapGrid;

    private final Map<String, Image> imageCache = new HashMap<>();
    private final Map<String, Canvas> canvasCache = new HashMap<>();

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
        String[] buildingTypes = {"apartment", "cementplant", "farm", "house", "lumbermill", "quary", "steelmill", "toolfactory", "woodencabin"};
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
            updateMapDisplay(col, row, building.getWidth(), building.getHeight());
        } else {
            System.out.println("Can't place building at " + row + ", " + col);
        }
    }

    private void initializeGrid() {
        int rows = GridMap.getHeight();
        int cols = GridMap.getWidth();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Image ground = getCachedImage("/eol/img_no_bg/ground.png");

                // Define the desired output width and height
                double outputWidth = 50; // Example width, you can set this to any value
                double outputHeight = 50; // Example height, you can set this to any value

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

    private void updateMapDisplay(int startX, int startY, int width, int height) {
        boolean[][] map = GridMap.getGrid();

        for (int row = startY; row < startY + height; row++) {
            for (int col = startX; col < startX + width; col++) {
                if (map[row][col]) {
                    String buildingType = BuildingCreatorController.getSelectedBuilding().toLowerCase();
                    String imagePath = "/eol/img_no_bg/buildings/" + buildingType + ".png";
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

                        mapGrid.add(canvas, startX, startY, width, height);
                        canvasCache.put(startY + "," + startX, canvas);
                    }
                }
            }
        }
    }

    private Image getCachedImage(String path) {
        if (!imageCache.containsKey(path)) {
            try {
                Image image = new Image(getClass().getResourceAsStream(path), 32, 32, false, false); // Disable smoothing and set dimensions
                imageCache.put(path, image);
            } catch (Exception e) {
                System.err.println("Image not found: " + path);
                return null;
            }
        }
        return imageCache.get(path);
    }
}
