package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Models.OrdenConDispositivo;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AsignacionActividadesController implements Initializable {
    @FXML Button btnAsignar;
    @FXML TableColumn<Tecnico,String> clmEspecialidad;
    @FXML TableColumn<Tecnico,String> clmCorreo;
    @FXML TableColumn<Tecnico,String> clmTelefono;
    @FXML TableColumn<Tecnico,String> clmNombre;
    @FXML TableColumn<Tecnico,Integer> clmIdTecnicos;
    @FXML TableView<Tecnico> tblTecnicos;
    @FXML ComboBox<String> cmbEstado;
    @FXML ComboBox<String> cmbTipoFalla;
    @FXML TableColumn<OrdenConDispositivo,String> clmDescripcion;
    @FXML TableColumn<OrdenConDispositivo,String> clmDispositivo;
    @FXML TableColumn<OrdenConDispositivo,String> clmEstado;
    @FXML TableColumn<OrdenConDispositivo,String> clmTipoFalla;
    @FXML TableColumn<OrdenConDispositivo, Date> clmFechaIngreso;
    @FXML TableView<OrdenConDispositivo> tblOrdenes;
    @FXML TableColumn<OrdenConDispositivo,Integer> clmIdOrdenes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTipoFalla.getItems().setAll("HARDWARE","SOFTWARE");
        cmbEstado.getItems().setAll("PARA-REVISION","PARA-REPARACION");
    }
    public void cmbTipoFalla_change(ActionEvent actionEvent) {
    }

    public void cmbEstado_change(ActionEvent actionEvent) {
    }

    public void btnAsignar_click(ActionEvent actionEvent) {
    }

}
