package eol.jfx.controllers;

import java.io.File;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BuildingCreatorController {

  @FXML private VBox buildingBox;

  @FXML
  public void initialize() {
    if (buildingBox == null) {
      System.err.println("buildingBox is null. Check fx:id in building.fxml.");
    } else {
      System.out.println("buildingBox is properly injected.");
      initializeBuildings();
    }
  }

  private void initializeBuildings() {
    try {
      // Chemin vers le dossier contenant les images
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
          Image image = new Image(file.toURI().toString());
          ImageView imageView = new ImageView(image);
          imageView.setFitWidth(50);
          imageView.setFitHeight(50);
          imageView.setPreserveRatio(true);

          buildingBox.getChildren().add(imageView);
        }
      } else {
        System.err.println("No image files found in the building folder.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}