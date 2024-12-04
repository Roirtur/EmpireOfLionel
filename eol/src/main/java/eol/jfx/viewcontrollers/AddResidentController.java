package eol.jfx.viewcontrollers;

import eol.jfx.managers.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AddResidentController {

    @FXML
    private TextField residentCountField;

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
            if (residentCountField.getText().isEmpty()) {
                showAlert("Error", "Please enter a number.");
            } else if (Integer.parseInt(residentCountField.getText()) < 1) {
                showAlert("Error", "Please enter a number greater than 0.");
            } else if (Integer.parseInt(residentCountField.getText()) > 100) {
                showAlert("Error", "Please enter a number less than 100.");
            }
            residentCount = Integer.parseInt(residentCountField.getText());

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid number format.");
            return;
        }

        if (residentCount > 0 && residentCount <= 100) {
            GameManager.addResident(residentCount);
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
