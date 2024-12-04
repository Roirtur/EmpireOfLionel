package eol.jfx.viewcontrollers;

import eol.jfx.managers.GameManager;
import eol.jfx.ressources.PlayerInventory;
import eol.jfx.ressources.Ressource;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AddResidentController {

  @FXML private TextField residentCountField;

  @FXML
  public void initialize() {
    // Add a listener to restrict input to digits only
    residentCountField.textProperty().addListener(
        (observable, oldValue, newValue) -> {
          if (!newValue.matches("\\d*")) {
            residentCountField.setText(newValue.replaceAll("[^\\d]", ""));
          }
        });
  }

  @FXML
  private void handleAddResident() {
    int residentCount = 1; // Default value
    try {
      if (!residentCountField.getText().isEmpty()) {
        residentCount = Integer.parseInt(residentCountField.getText());
      }
    } catch (NumberFormatException e) {
      showAlert("Error", "Invalid number format.");
      return;
    }

    int foodRequired = residentCount * 2;
    int currentFood = PlayerInventory.getRessourceQuantity(Ressource.FOOD);
    if (currentFood >= foodRequired) {
      PlayerInventory.productRessource(Ressource.FOOD, -foodRequired);
      for (int i = 0; i < residentCount; i++) {
        GameManager.addResident();
      }
    } else {
      int foodMissing = foodRequired - currentFood;
      showAlert("Error", "Not enough food to add " + residentCount +
                             " resident(s). You need " + foodMissing +
                             " more food.");
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}