package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Models.OrdenConDispositivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

import java.util.Date;

public class ReparacionesController {
    @FXML Button btnVerReparacion;
    @FXML ComboBox<String> cmbFiltro;
    @FXML TableColumn<OrdenConDispositivo,String> clmDescripcion;
    @FXML TableColumn<OrdenConDispositivo,String> clmDispositivo;
    @FXML TableColumn<OrdenConDispositivo,String> clmTipoFalla;
    @FXML TableColumn<OrdenConDispositivo,String> clmEstado;
    @FXML TableColumn<OrdenConDispositivo,Date> clmFechaEgreso;
    @FXML TableColumn<OrdenConDispositivo,Date> clmFechaIngreso;
    @FXML TableColumn<OrdenConDispositivo,Integer> clmId;

    public void cmbFiltro_change(ActionEvent actionEvent) {
    }

    public void btnVerReparacion_click(ActionEvent actionEvent) {
    }
}
