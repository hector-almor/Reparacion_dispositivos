package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.CargableConId;
import com.tecnolofix.reparacion_electronicos.Controllers.Contexto;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ReparacionesController implements Initializable, ControladorConRootPane {
    @FXML ComboBox<String> cmbFiltroFalla;
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
        cmbFiltro.getSelectionModel().select("TODOS");

        cmbFiltroFalla.getItems().setAll("TODOS","HARDWARE","SOFTWARE");
        cmbFiltroFalla.getSelectionModel().select("TODOS");

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIng"));
        clmFechaEgreso.setCellValueFactory(new PropertyValueFactory<>("fechaEg"));
        clmTipoFalla.setCellValueFactory(new PropertyValueFactory<>("tipoFalla"));
        clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        clmDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        clmFechaEgreso.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null); // <- importante para celdas vacías
                } else if (item == null) {
                    setText("Sin fecha");
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

        clmFechaIngreso.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        ArrayList<OrdenConDispositivo> ordenes = db.obtenerOrdenReparacionConDispositivo();
        ordenConDispositivos.setAll(ordenes);
        listaFiltrada = new FilteredList<>(ordenConDispositivos,o->true);
        tblReparaciones.setItems(listaFiltrada);
    }

    public void cmbFiltro_change(ActionEvent actionEvent) {
        String filtroEstado = cmbFiltro.getSelectionModel().getSelectedItem();
        String filtroFalla = cmbFiltroFalla.getSelectionModel().getSelectedItem();

        listaFiltrada.setPredicate(orden-> {
            boolean coincideFalla = filtroFalla.equals("TODOS") || orden.getTipoFalla().name().equalsIgnoreCase(filtroFalla);
            boolean coincideEstado = filtroEstado.equals("TODOS") || orden.getEstado().name().equalsIgnoreCase(filtroEstado);
            return coincideFalla && coincideEstado;
        });
    }
    public void cmbFiltroFalla_change(ActionEvent actionEvent) {
        String filtroEstado = cmbFiltro.getSelectionModel().getSelectedItem();
        String filtroFalla = cmbFiltroFalla.getSelectionModel().getSelectedItem();

        listaFiltrada.setPredicate(orden-> {
            boolean coincideFalla = filtroFalla.equals("TODOS") || orden.getTipoFalla().name().equalsIgnoreCase(filtroFalla);
            boolean coincideEstado = filtroEstado.equals("TODOS") || orden.getEstado().name().equalsIgnoreCase(filtroEstado);
            return coincideFalla && coincideEstado;
        });
    }

    public void btnVerReparacion_click(ActionEvent actionEvent) {
        if(tblReparaciones.getSelectionModel().getSelectedItem()==null){
            Alerts.showAlert("Error","Seleccionar una reparacion a inspeccionar.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            return;
        }
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/DetalleReparacion.fxml"));
            loader.setControllerFactory(param-> new DetalleReparacionController(tblReparaciones.getSelectionModel().getSelectedItem().getId()));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

//            if(controlador instanceof DetalleReparacionController){
//                ((DetalleReparacionController) controlador).setId(tblReparaciones.getSelectionModel().getSelectedItem().getId());
//                ((DetalleReparacionController) controlador).cargarDatos();
//            }
//            Contexto.idRevision = tblReparaciones.getSelectionModel().getSelectedItem().getId();

//            if(controlador instanceof CargableConId){
//                ((CargableConId) controlador).setId(tblReparaciones.getSelectionModel().getSelectedItem().getId());
//                ((CargableConId) controlador).cargarDatos();
//            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
