module eol.jfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens eol.jfx to javafx.fxml;
    exports eol.jfx;
}
