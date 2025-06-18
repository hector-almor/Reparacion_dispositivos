package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.DAO.TecnicoDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.TecnicoDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.OrdenConDispositivo;
import com.tecnolofix.reparacion_electronicos.Models.OrdenReparacion;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML TableColumn<OrdenConDispositivo,Integer> clmIdOrdenes;
    @FXML TableView<OrdenConDispositivo> tblOrdenes;

    ObservableList<OrdenConDispositivo> listaOrdenes = FXCollections.observableArrayList();
    ObservableList<Tecnico> listaTecnicos = FXCollections.observableArrayList();

        FilteredList<OrdenConDispositivo> listaFiltradaOrdenes;
        FilteredList<Tecnico> listaFiltradaTecnicos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTipoFalla.getItems().setAll("TODOS","HARDWARE","SOFTWARE");
        cmbEstado.getItems().setAll("TODOS","REVISION","REPARACION");

        cmbEstado.getSelectionModel().select("TODOS");
        cmbTipoFalla.getSelectionModel().select("TODOS");

        clmIdOrdenes.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIng"));
        clmTipoFalla.setCellValueFactory(new PropertyValueFactory<>("tipoFalla"));
        clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        clmDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        clmIdTecnicos.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        clmEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));

        OrdenReparacionDAO dbOrden = new OrdenReparacionDAOImp();
        ArrayList<OrdenConDispositivo> ordenes = dbOrden.obtenerOrdenesConDispositivo();
        listaOrdenes.setAll(ordenes);

        TecnicoDAO dbTecnico = new TecnicoDAOImp();
        ArrayList<Tecnico> tecnicos = dbTecnico.getAllTecnicos();
        listaTecnicos.setAll(tecnicos);

        // Inicializas los filtros envolviendo las listas originales
        listaFiltradaOrdenes = new FilteredList<>(listaOrdenes, o -> true);
        listaFiltradaTecnicos = new FilteredList<>(listaTecnicos, t -> true);

        // Enlazas la tabla a la lista filtrada
        tblOrdenes.setItems(listaFiltradaOrdenes);
        tblTecnicos.setItems(listaFiltradaTecnicos);
    }

    private void filtrarOrdenes(){
        String filtroFalla = cmbTipoFalla.getSelectionModel().getSelectedItem();
        String filtroEstado = cmbEstado.getSelectionModel().getSelectedItem();

        listaFiltradaOrdenes.setPredicate(orden -> {
            boolean coincideFalla = filtroFalla.equals("TODOS") || orden.getTipoFalla().name().equalsIgnoreCase(filtroFalla);
            boolean coincideEstado = filtroEstado.equals("TODOS") || orden.getTipoOrden().name().equalsIgnoreCase(filtroEstado);
            return coincideFalla && coincideEstado;
        });
    }

    private void filtrarTecnicos(){
        String filtroFalla = cmbTipoFalla.getValue(); // "HARDWARE", "SOFTWARE" o "TODOS"

        listaFiltradaTecnicos.setPredicate(tecnico -> {
            if (filtroFalla == null || filtroFalla.equals("TODOS")) {
                return true;
            }

            return tecnico.getEspecialidad().name().equalsIgnoreCase(filtroFalla);
        });
    }

    public void cmbTipoFalla_change(ActionEvent actionEvent) {
        filtrarTecnicos();
        filtrarOrdenes();
    }

    public void cmbEstado_change(ActionEvent actionEvent) {
        filtrarOrdenes();
        filtrarTecnicos();
    }

    public void btnAsignar_click(ActionEvent actionEvent) {
        if(tblTecnicos.getSelectionModel().getSelectedItem()==null||tblOrdenes.getSelectionModel().getSelectedItem()==null){
            Alerts.showAlert("Error","Debe seleccionar una orden para ser asignada a un ténico.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            return;
        }
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.enlazaOrdenConTecnico(tblOrdenes.getSelectionModel().getSelectedItem().getId(),tblTecnicos.getSelectionModel().getSelectedItem().getId())){
            Alerts.showAlert("Éxito","Se le ha asignado la tarea al técnico correctamente.",Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
            OrdenConDispositivo resultado = listaOrdenes.stream()
                    .filter(c -> c.getId()==tblOrdenes.getSelectionModel().getSelectedItem().getId())
                    .findFirst()
                    .orElse(null);
            if(resultado!=null){
                resultado.setEstado(OrdenReparacion.Estado.ASIGNADO);
//                tblOrdenes.refresh();
            }
            listaFiltradaOrdenes = new FilteredList<>(listaOrdenes, o -> true);
            listaFiltradaTecnicos = new FilteredList<>(listaTecnicos, t -> true);
            tblOrdenes.setItems(listaFiltradaOrdenes);
            tblTecnicos.setItems(listaFiltradaTecnicos);
        }else {
            Alerts.showAlert("Error", "No se ha podido asignar la tarea al técnico. Intente de nuevo", Alert.AlertType.ERROR, new ButtonType[]{ButtonType.OK});
        }
    }

}
