module eol.jfx {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.base;
  requires transitive javafx.graphics;
  opens eol.jfx.viewcontrollers to javafx.fxml;

  exports eol.jfx;
}
