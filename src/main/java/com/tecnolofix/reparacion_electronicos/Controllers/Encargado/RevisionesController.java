package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.OrdenConDispositivo;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class RevisionesController implements Initializable, ControladorConRootPane {
    @FXML TableView<OrdenConDispositivo> tblRevisiones;
    @FXML Button btnVerRevision;
    @FXML ComboBox<String> cmbFiltro;
    @FXML TableColumn<OrdenConDispositivo,String> clmDescripcion;
    @FXML TableColumn<OrdenConDispositivo,String> clmDispositivo;
    @FXML TableColumn<OrdenConDispositivo,String> clmTipoFalla;
    @FXML TableColumn<OrdenConDispositivo,String> clmEstado;
    @FXML TableColumn<OrdenConDispositivo, Date> clmFechaIngreso;
    @FXML TableColumn<OrdenConDispositivo,Integer> clmId;

    ObservableList<OrdenConDispositivo> listaOrdenes = FXCollections.observableArrayList();

    FilteredList<OrdenConDispositivo> listaFiltradaOrdenes;

    private BorderPane rootPane;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbFiltro.getItems().setAll("TODOS","ASIGNADO","PROGRESO","COMPLETADO","ENTREGADO","CANCELADO");
        cmbFiltro.getSelectionModel().select("TODOS");

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIng"));
        clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        clmTipoFalla.setCellValueFactory(new PropertyValueFactory<>("tipoFalla"));
        clmDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        ArrayList<OrdenConDispositivo> ordenes = db.getAllOrdenesConDispositivo();
        listaOrdenes.setAll(ordenes);

        listaFiltradaOrdenes = new FilteredList<>(listaOrdenes,o->true);
        tblRevisiones.setItems(listaFiltradaOrdenes);
    }
    public void cmbFiltro_change(ActionEvent actionEvent) {
        String filtro = cmbFiltro.getSelectionModel().getSelectedItem();

        listaFiltradaOrdenes.setPredicate(orden->
            orden.getEstado().name().equalsIgnoreCase(filtro)
        );
    }

    public void btnVerRevision_click(ActionEvent actionEvent) {
        if(tblRevisiones.getSelectionModel().getSelectedItem()==null){
            Alerts.showAlert("Error","Seleccionar una revisión a inspeccionar.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/DetalleRevision.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root
            // Obtener el controlador de esa vista
            Object controlador = loader.getController();
            // Si el controlador tiene un metodo para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if(controlador instanceof DetalleRevisionController){
                ((DetalleRevisionController) controlador).setIdRevision(tblRevisiones.getSelectionModel().getSelectedItem().getId());
                ((DetalleRevisionController) controlador).cargarDatos();
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
