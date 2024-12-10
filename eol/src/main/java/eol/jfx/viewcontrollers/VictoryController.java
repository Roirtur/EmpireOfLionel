package eol.jfx.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VictoryController {

  @FXML private Button okButton;

  @FXML
  private void handleOkButton() {
    Stage stage = (Stage)okButton.getScene().getWindow();
    stage.close();
  }
}