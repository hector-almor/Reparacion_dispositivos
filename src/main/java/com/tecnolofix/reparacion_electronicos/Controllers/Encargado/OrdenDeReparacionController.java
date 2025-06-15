package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdenDeReparacionController implements Initializable {
    @FXML ComboBox<String> cmbTipoFalla;
    @FXML TextField txtModelo;
    @FXML TextArea txtDescripcionFalla;
    @FXML Button btnRegistrar;
    @FXML TextField txtNombreCliente;
    @FXML TextField txtMarca;
    @FXML TextField txtNombreDispositivo;

    public void btnRegistrar_click(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTipoFalla.getItems().setAll("SOFTWARE","HARDWARE");
    }
}
