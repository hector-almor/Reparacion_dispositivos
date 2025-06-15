package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Models.OrdenConDispositivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class RevisionesController implements Initializable {
    @FXML Button btnVerRevision;
    @FXML ComboBox<String> cmbFiltro;
    @FXML TableColumn<OrdenConDispositivo,String> clmDescripcion;
    @FXML TableColumn<OrdenConDispositivo,String> clmDispositivo;
    @FXML TableColumn<OrdenConDispositivo,String> clmTipoFalla;
    @FXML TableColumn<OrdenConDispositivo,String> clmEstado;
    @FXML TableColumn<OrdenConDispositivo,Date> clmFechaEgreso;
    @FXML TableColumn<OrdenConDispositivo, Date> clmFechaIngreso;
    @FXML TableColumn<OrdenConDispositivo,Integer> clmId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbFiltro.getItems().setAll("TODAS","EN-REVISION","REVISADO");
    }
    public void cmbFiltro_change(ActionEvent actionEvent) {
    }

    public void btnVerRevision_click(ActionEvent actionEvent) {
    }

}
