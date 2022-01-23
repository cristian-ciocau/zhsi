module zhsi {
    requires java.logging;
    requires java.desktop;

    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;

    opens org.andreels.zhsi to javafx.fxml;

    exports org.andreels.zhsi;
}