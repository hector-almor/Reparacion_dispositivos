package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReparacionesController {
    @FXML Button btnDetalleReparacion;
    @FXML TableColumn clmDescripcion;
    @FXML TableColumn clmDispositivo;
    @FXML TableColumn clmTipoFalla;
    @FXML TableColumn clmEstado;
    @FXML TableColumn clmTipoReparacion;
    @FXML TableColumn clmFechaIngreso;
    @FXML TableColumn clmId;
    @FXML TableView tblReparaciones;

    public void btnDetalleReparacion_click(ActionEvent actionEvent) {

    }
}
