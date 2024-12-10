package eol.jfx.viewcontrollers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import eol.jfx.observers.Observer;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ResourceController implements Observer {

    @FXML
    private FlowPane resourceBox;

    private final Map<String, Image> imageCache = new HashMap<>();

    @FXML
    public void initialize() {
        if (resourceBox == null) {
            System.err.println("resourceBox is null. Check fx:id in resources.fxml.");
        } else {
            // System.out.println("resourceBox is properly injected.");
            preloadImages();
            resourceBox.setHgap(20);
            resourceBox.setVgap(20);
            resourceBox.setPrefHeight(200);

            initializeResources();
            PlayerInventory.registerObserver(this); // Register as observer
        }
    }

    private void preloadImages() {
        // Preload all resource images
        String[] resourceTypes = {"food", "wood", "stone", "tools", "cement",
            "coal", "iron", "steel", "lumber"};
        for (String type : resourceTypes) {
            String imagePath = "/eol/img_no_bg/resources/" + type + ".png";
            getCachedImage(imagePath);
        }
    }

    private void initializeResources() {
        try {
            URL resourceFolderUrl
                    = getClass().getResource("/eol/img_no_bg/resources");
            if (resourceFolderUrl == null) {
                System.err.println("Resource folder not found.");
                return;
            }

            File resourceFolder = new File(resourceFolderUrl.toURI());
            File[] files
                    = resourceFolder.listFiles((dir, name) -> name.endsWith(".png"));

            if (files != null) {
                for (File file : files) {
                    String resourceName = file.getName().replace(".png", "").toUpperCase();
                    Ressource ressource = Ressource.valueOf(resourceName);

                    String imagePath = "/eol/img_no_bg/resources/" + file.getName();
                    Image image = getCachedImage(imagePath);

                    // Define the desired output width and height
                    double outputWidth
                            = 50; // Example width, you can set this to any value
                    double outputHeight
                            = 50; // Example height, you can set this to any value

                    Canvas canvas = new Canvas(outputWidth, outputHeight);
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setImageSmoothing(false); // Disable image smoothing
                    gc.drawImage(image, 0, 0, outputWidth, outputHeight);

                    int quantity = PlayerInventory.getRessourceQuantity(ressource);
                    String labelText = String.valueOf(quantity);

                    if (resourceName.equals("RESIDENTS")) {
                        int maxResidents = PlayerInventory.getRessourceQuantity(Ressource.MAXRESIDENTS);
                        labelText += " / " + maxResidents;
                    }

                    Label quantityLabel = new Label(labelText);
                    Label nameLabel = new Label(ressource.toString());

                    VBox vbox = new VBox(nameLabel, canvas, quantityLabel);
                    vbox.setSpacing(5);
                    vbox.setPrefWidth(85);
                    vbox.setAlignment(Pos.CENTER);
                    resourceBox.getChildren().add(vbox);
                }
            } else {
                System.err.println("No image files found in the resource folder.");
            }
        } catch (URISyntaxException e) {
        }
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            resourceBox.getChildren().clear();
            initializeResources();
        });
    }

    private Image getCachedImage(String path) {
        if (!imageCache.containsKey(path)) {
            try {
                Image image
                        = new Image(getClass().getResourceAsStream(path), 32, 32, false,
                                false); // Disable smoothing and set dimensions
                imageCache.put(path, image);
            } catch (Exception e) {
                System.err.println("ResourceController - Image not found: " + path);
                return null;
            }
        }
        return imageCache.get(path);
    }
}
