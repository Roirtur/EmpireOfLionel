package eol.jfx.controllers;

import java.io.File;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ResourceController {

  @FXML private HBox resourceBox;

  @FXML
  public void initialize() {
    if (resourceBox == null) {
      System.err.println("resourceBox is null. Check fx:id in resources.fxml.");
    } else {
      System.out.println("resourceBox is properly injected.");
      initializeResources();
    }
  }

  private void initializeResources() {
    try {
      // Chemin vers le dossier contenant les images
      URL resourceFolderUrl =
          getClass().getResource("/eol/img_no_bg/resources");
      if (resourceFolderUrl == null) {
        System.err.println("Resource folder not found.");
        return;
      }

      File resourceFolder = new File(resourceFolderUrl.toURI());
      File[] files =
          resourceFolder.listFiles((dir, name) -> name.endsWith(".png"));

      if (files != null) {
        for (File file : files) {
          Image image = new Image(file.toURI().toString());
          ImageView imageView = new ImageView(image);
          imageView.setFitWidth(50);
          imageView.setFitHeight(50);
          imageView.setPreserveRatio(true);
          resourceBox.getChildren().add(imageView);
        }
      } else {
        System.err.println("No image files found in the resource folder.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}