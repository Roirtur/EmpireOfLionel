package eol.jfx.viewcontrollers;

import java.io.File;
import java.net.URL;

import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ResourceController {

  @FXML private VBox resourceBox;

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
          String fileName = file.getName().replace(".png", "").toUpperCase();
          Ressource ressource = Ressource.valueOf(fileName);

          Image image = new Image(file.toURI().toString());
          ImageView imageView = new ImageView(image);
          imageView.setFitWidth(50);
          imageView.setFitHeight(50);
          imageView.setPreserveRatio(true);

          int quantity = PlayerInventory.getRessourceQuantity(ressource);
          Label quantityLabel = new Label(String.valueOf(quantity));

          VBox vbox = new VBox(imageView, quantityLabel);
          vbox.setSpacing(5);
          resourceBox.getChildren().add(vbox);
        }
      } else {
        System.err.println("No image files found in the resource folder.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}