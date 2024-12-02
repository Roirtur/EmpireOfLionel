module eol.jfx {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.base;

  opens eol.jfx.viewcontrollers to javafx.fxml;

  exports eol.jfx;
}