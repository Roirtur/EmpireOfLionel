
module eol.jfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;


    opens eol.jfx to javafx.fxml;
    exports eol.jfx;
}
