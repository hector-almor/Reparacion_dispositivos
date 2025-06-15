package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Models.Pieza;
import com.tecnolofix.reparacion_electronicos.Models.Proveedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ComprarPiezasController {
    @FXML TextField txtNombre;
    @FXML Button btnRegistrar;
    @FXML TableColumn<Pieza,Integer> clmCantidad;
    @FXML TableColumn<Pieza,Double> clmCosto;
    @FXML TableColumn<Pieza,String> clmDescripcion;
    @FXML TableColumn<Pieza,String> clmNombre;
    @FXML TableColumn<Pieza,String> clmId;
    @FXML TableView<Pieza> tblPiezas;
    @FXML Button btnAgregar;
    @FXML TextField txtCantidad;
    @FXML TextField txtCosto;
    @FXML TextField txtDescripcion;
    @FXML TextField txtId;
    @FXML ComboBox<Proveedor> cmbProveedor;

    public void btnAgregar_click(ActionEvent actionEvent) {
    }

    public void btnRegistrar_click(ActionEvent actionEvent) {
    }
}
