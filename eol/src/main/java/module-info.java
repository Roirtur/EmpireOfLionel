module eol.jfx {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.base;

  opens eol.jfx.controllers to javafx.fxml;

  exports eol.jfx;
}