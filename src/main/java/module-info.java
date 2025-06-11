module com.tecnolofix.reparacion_electronicos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.tecnolofix.reparacion_electronicos to javafx.fxml;
    exports com.tecnolofix.reparacion_electronicos;
    exports com.tecnolofix.reparacion_electronicos.Controllers;
    opens com.tecnolofix.reparacion_electronicos.Controllers to javafx.fxml;
}