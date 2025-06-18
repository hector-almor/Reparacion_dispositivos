package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.OrdenCompleta;
import com.tecnolofix.reparacion_electronicos.Models.OrdenConDispositivo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ReparacionesController implements Initializable, ControladorConRootPane {
    @FXML TableView<OrdenConDispositivo> tblReparaciones;
    @FXML Button btnVerReparacion;
    @FXML ComboBox<String> cmbFiltro;
    @FXML TableColumn<OrdenConDispositivo,String> clmDescripcion;
    @FXML TableColumn<OrdenConDispositivo,String> clmDispositivo;
    @FXML TableColumn<OrdenConDispositivo,String> clmTipoFalla;
    @FXML TableColumn<OrdenConDispositivo,String> clmEstado;
    @FXML TableColumn<OrdenConDispositivo, LocalDate> clmFechaEgreso;
    @FXML TableColumn<OrdenConDispositivo,LocalDate> clmFechaIngreso;
    @FXML TableColumn<OrdenConDispositivo,Integer> clmId;

    ObservableList<OrdenConDispositivo> ordenConDispositivos = FXCollections.observableArrayList();
    FilteredList<OrdenConDispositivo> listaFiltrada;
    private BorderPane rootPane;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbFiltro.getItems().setAll("TODOS","ASIGNADO","PROGRESO","COMPLETADO","ENTREGADO");

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIng"));
        clmFechaEgreso.setCellValueFactory(new PropertyValueFactory<>("fechaEg"));
        clmTipoFalla.setCellValueFactory(new PropertyValueFactory<>("tipoFalla"));
        clmDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        ArrayList<OrdenConDispositivo> ordenes = db.obtenerOrdenReparacionConDispositivo();
        ordenConDispositivos.setAll(ordenes);
        listaFiltrada = new FilteredList<>(ordenConDispositivos,o->true);
        tblReparaciones.setItems(listaFiltrada);
    }

    public void cmbFiltro_change(ActionEvent actionEvent) {
        listaFiltrada.setPredicate(orden->
            orden.getTipoFalla().name().equalsIgnoreCase("TODOS") || orden.getEstado().name().equalsIgnoreCase(cmbFiltro.getSelectionModel().getSelectedItem())
        );
    }

    public void btnVerReparacion_click(ActionEvent actionEvent) {
        if(tblReparaciones.getSelectionModel().getSelectedItem()==null){
            Alerts.showAlert("Error","Seleccionar una reparacion a inspeccionar.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/DetalleReparacion.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if(controlador instanceof DetalleReparacionController){
                ((DetalleReparacionController) controlador).setIdReparacion(tblReparaciones.getSelectionModel().getSelectedItem().getId());
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
