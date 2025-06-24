module com.tecnolofix.reparacion_electronicos {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires kernel;
    requires layout;
    requires java.desktop;
    requires io;
    requires org.bouncycastle.provider;
    requires java.sql;


    opens com.tecnolofix.reparacion_electronicos to javafx.fxml;
    exports com.tecnolofix.reparacion_electronicos;
    exports com.tecnolofix.reparacion_electronicos.Controllers;
    opens com.tecnolofix.reparacion_electronicos.Controllers to javafx.fxml;
    exports com.tecnolofix.reparacion_electronicos.Controllers.Encargado;
    opens com.tecnolofix.reparacion_electronicos.Controllers.Encargado to javafx.fxml;
    exports com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;
    opens com.tecnolofix.reparacion_electronicos.Controllers.Tecnico to javafx.fxml;
    exports com.tecnolofix.reparacion_electronicos.Models;
    opens com.tecnolofix.reparacion_electronicos.Models to javafx.fxml;
}