module FinalProjectBNCCjava {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    
    opens tokoshoe.main to javafx.graphics, javafx.fxml;
    exports tokoshoe.view to javafx.graphics, javafx.fxml;
}
