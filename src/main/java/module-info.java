module com.example.sierpinskitriangle {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens com.sierpinskitriangle to javafx.fxml;
    exports com.sierpinskitriangle;
}