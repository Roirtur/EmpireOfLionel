package eol.jfx.viewcontrollers;

import java.io.File;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BuildingCreatorController {

    @FXML
    private HBox buildingBox;

    @FXML
    public void initialize() {
        if (buildingBox == null) {
            System.err.println("buildingBox is null. Check fx:id in building.fxml.");
        } else {
            System.out.println("buildingBox is properly injected.");
            initializeBuildings();
        }
    }

    private static String buildingName = "";

    private void setSelectedBuilding(Button building) {

        String buildingType = building.getId();
        if (buildingName.equals(buildingType)) {
            buildingName = "";
        } else if (buildingName.equals("")) {
            buildingName = buildingType;
        }
        System.out.println("Selected building: " + buildingName);
    }

    public static String getSelectedBuilding() {
        return buildingName;
    }

    private void initializeBuildings() {
        try {
            // Chemin vers le dossier contenant les images
            URL buildingFolderUrl
                    = getClass().getResource("/eol/img_no_bg/buildings");
            if (buildingFolderUrl == null) {
                System.err.println("Building folder not found.");
                return;
            }

            File buildingFolder = new File(buildingFolderUrl.toURI());
            File[] files
                    = buildingFolder.listFiles((dir, name) -> name.endsWith(".png"));

            if (files != null) {
                for (File file : files) {
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setPreserveRatio(true);

                    Button button = new Button();
                    button.setGraphic(imageView);
                    button.setId(file.getName().replace(".png", ""));
                    button.getStyleClass().add("image-button");
                    button.setOnAction(event -> setSelectedBuilding(button));

                    buildingBox.getChildren().add(button);
                }
            } else {
                System.err.println("No image files found in the building folder.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
