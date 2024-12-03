package eol.jfx.viewcontrollers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import eol.jfx.observers.Observer;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ResourceController implements Observer {

    @FXML
    private VBox resourceBox;

    private final Map<String, Image> imageCache = new HashMap<>();

    @FXML
    public void initialize() {
        if (resourceBox == null) {
            System.err.println("resourceBox is null. Check fx:id in resources.fxml.");
        } else {
            System.out.println("resourceBox is properly injected.");
            preloadImages();
            initializeResources();
            PlayerInventory.registerObserver(this); // Register as observer
        }
    }

    private void preloadImages() {
        // Preload all resource images
        String[] resourceTypes = {"food", "wood", "stone", "tools", "cement", "coal", "iron", "steel", "lumber"};
        for (String type : resourceTypes) {
            String imagePath = "/eol/img_no_bg/resources/" + type + ".png";
            getCachedImage(imagePath);
        }
    }

    private void initializeResources() {
        try {
            URL resourceFolderUrl = getClass().getResource("/eol/img_no_bg/resources");
            if (resourceFolderUrl == null) {
                System.err.println("Resource folder not found.");
                return;
            }

            File resourceFolder = new File(resourceFolderUrl.toURI());
            File[] files = resourceFolder.listFiles((dir, name) -> name.endsWith(".png"));

            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName().replace(".png", "").toUpperCase();
                    Ressource ressource = Ressource.valueOf(fileName);

                    String imagePath = "/eol/img_no_bg/resources/" + file.getName();
                    Image image = getCachedImage(imagePath);
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

    @Override
    public void update() {
        resourceBox.getChildren().clear();
        initializeResources();
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
