module com.tecnolofix.reparacion_electronicos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tecnolofix.reparacion_electronicos to javafx.fxml;
    exports com.tecnolofix.reparacion_electronicos;
}