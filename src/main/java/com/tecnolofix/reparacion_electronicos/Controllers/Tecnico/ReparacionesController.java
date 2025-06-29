package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
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
import java.util.ResourceBundle;

public class ReparacionesController implements Initializable, ControladorConRootPane {
    @FXML ComboBox<String> cmbFiltro;
    @FXML Button btnDetalleReparacion;
    @FXML TableColumn<OrdenConDispositivo,String> clmDescripcion;
    @FXML TableColumn<OrdenConDispositivo,String> clmDispositivo;
    @FXML TableColumn<OrdenConDispositivo,String> clmTipoFalla;
    @FXML TableColumn<OrdenConDispositivo,String> clmEstado;
    @FXML TableColumn<OrdenConDispositivo,String> clmTipoReparacion;
    @FXML TableColumn<OrdenConDispositivo, LocalDate> clmFechaIngreso;
    @FXML TableColumn<OrdenConDispositivo,Integer> clmId;
    @FXML TableView<OrdenConDispositivo> tblReparaciones;

    private BorderPane rootPane;

    ObservableList<OrdenConDispositivo> ordenConDispositivos = FXCollections.observableArrayList();
    FilteredList<OrdenConDispositivo> listaFiltrada;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public ReparacionesController(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public ReparacionesController(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbFiltro.getItems().setAll("TODOS","REVISION","REPARACION");
        cmbFiltro.getSelectionModel().select("TODOS");

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIng"));
        clmTipoFalla.setCellValueFactory(new PropertyValueFactory<>("tipoFalla"));
        clmTipoReparacion.setCellValueFactory(new PropertyValueFactory<>("tipoOrden"));
        clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        clmTipoFalla.setCellValueFactory(new PropertyValueFactory<>("tipoFalla"));
        clmDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        ArrayList<OrdenConDispositivo> ordenes = db.obtenerOrdenesDeTecnico(PrincipalTecnicoController.sesionTecnico.getId());
        ordenConDispositivos.setAll(ordenes);
        listaFiltrada = new FilteredList<>(ordenConDispositivos, o->true);
        tblReparaciones.setItems(listaFiltrada);

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


    }

    public void cmbFiltro_changed(ActionEvent actionEvent) {
        listaFiltrada.setPredicate(orden -> {
            String cmbfiltro = cmbFiltro.getSelectionModel().getSelectedItem();
            return cmbfiltro.equals("TODOS") || orden.getTipoOrden().name().equalsIgnoreCase(cmbfiltro);
        });
    }

    public void btnDetalleReparacion_click(ActionEvent actionEvent) {
        if(tblReparaciones.getSelectionModel().getSelectedItem()==null){
            Alerts.showAlert("Error","Selecciona antes una reparación.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
        }else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparar.fxml"));
                loader.setControllerFactory(p->new RepararController(tblReparaciones.getSelectionModel().getSelectedItem().getId()));
                Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

                // Obtener el controlador de esa vista
                Object controlador = loader.getController();

                // Si el controlador tiene un método para recibir el rootPane, lo llamas:
                if (controlador instanceof ControladorConRootPane) {
                    ((ControladorConRootPane) controlador).setRootPane(rootPane);
                }

                rootPane.setCenter(vistaCentro);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
