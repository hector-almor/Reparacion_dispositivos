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

public class OrdenDeRevisionController implements Initializable {
    @FXML public TextField txtTelefono;
    @FXML Button btnRegistrar;
    @FXML TextArea txtDescripcion;
    @FXML ComboBox<String> cmbTipoFalla;
    @FXML ComboBox<String> cmbDispositivo;
    @FXML TextArea txtObservaciones;
    @FXML TextField txtModelo;
    @FXML TextField txtMarca;
    @FXML TextField txtNombreDispositivo;
    @FXML TextField txtCorreo;
    @FXML TextField txtNombreCliente;

    public void btnRegistrar_click(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTipoFalla.getItems().addAll("HARDWARE", "SOFTWARE");
        cmbDispositivo.getItems().addAll("LAPTOP","PC","CELULAR","TABLET");
    }
}
