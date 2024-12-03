package eol.jfx.viewcontrollers;

import java.util.HashMap;
import java.util.Map;

import eol.jfx.buildings.Building;
import eol.jfx.buildings.BuildingFactory;
import eol.jfx.buildings.BuildingType;
import eol.jfx.managers.GameManager;
import eol.jfx.managers.GridMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MapController {

    @FXML
    private GridPane mapGrid;

    private final Map<String, Image> imageCache = new HashMap<>();
    private final Map<String, ImageView> imageViewCache = new HashMap<>();

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
        boolean[][] map = GridMap.getGrid();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Image ground = getCachedImage("/eol/img_no_bg/ground.png");
                ImageView imageView = new ImageView(ground);

                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(false);

                Button button = new Button();
                button.setGraphic(imageView);
                button.getStyleClass().add("image-button");
                final int finalRow = row;
                final int finalCol = col;
                button.setOnAction(event -> putBuildingOnMap(finalRow, finalCol));

                mapGrid.add(button, col, row);
                imageViewCache.put(row + "," + col, imageView);
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
                        ImageView buildingImageView = imageViewCache.get(row + "," + col);
                        if (buildingImageView == null) {
                            buildingImageView = new ImageView(buildingImage);
                            buildingImageView.setFitWidth(50);
                            buildingImageView.setFitHeight(50);
                            buildingImageView.setPreserveRatio(true);
                            buildingImageView.setSmooth(false);
                            mapGrid.add(buildingImageView, col, row);
                            imageViewCache.put(row + "," + col, buildingImageView);
                        } else {
                            buildingImageView.setImage(buildingImage);
                        }
                    }
                }
            }
        }
    }

    private Image getCachedImage(String path) {
        if (!imageCache.containsKey(path)) {
            try {
                Image image = new Image(getClass().getResourceAsStream(path));
                imageCache.put(path, image);
            } catch (Exception e) {
                System.err.println("Image not found: " + path);
                return null;
            }
        }
        return imageCache.get(path);
    }
}
