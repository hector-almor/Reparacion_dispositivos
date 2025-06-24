package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.*;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.*;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Herramienta;
import com.tecnolofix.reparacion_electronicos.Models.Pieza;
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
import java.util.ResourceBundle;

public class UsarPiezasController implements Initializable, ControladorConRootPane {
    @FXML Button btnRegresar;
    @FXML TableColumn<Pieza,Double> clmCostoUnitario;
    @FXML TableColumn<Pieza,Integer> clmStockUso;
    @FXML TableColumn<Pieza,String> clmDescripcionUso;
    @FXML TableColumn<Pieza,String> clmNNombreUso;
    @FXML TableColumn<Pieza,String> clmIdUso;
    @FXML TableView<Pieza> tblPiezasUso;
    @FXML Spinner<Integer> spnrCantidadUsar;
    @FXML Button btnUsar;
    @FXML TextField txtFiltro;
    @FXML TableColumn<Pieza,Double> clmCosto;
    @FXML TableColumn<Pieza,Integer> clmStock;
    @FXML TableColumn<Pieza,String> clmDescripcion;
    @FXML TableColumn<Pieza,String> clmNombre;
    @FXML TableColumn<Pieza,String> clmId;
    @FXML TableView<Pieza> tblPiezas;

    private int idReparacion;
    private BorderPane rootPane;

    ObservableList<Pieza> observablePiezas = FXCollections.observableArrayList();
    FilteredList<Pieza> filteredPiezas = new FilteredList<>(observablePiezas);

    ObservableList<Pieza> observablePiezasUso = FXCollections.observableArrayList();

    Pieza piezaSeleccionada = null;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setIdOrden(int idOrden) {
        this.idReparacion = idOrden;
    }

    public UsarPiezasController(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        spnrCantidadUsar.setValueFactory(valueFactory);
        spnrCantidadUsar.setEditable(true);


        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        clmCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        PiezaDAO db = new PiezaDAOImp();
        ArrayList<Pieza> listaPiezas = db.getAllPiezas();
        observablePiezas.setAll(listaPiezas);
        filteredPiezas.setPredicate(p->true);
        tblPiezas.setItems(filteredPiezas);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPiezas.setPredicate(h -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return h.getNombre().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        clmIdUso.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNNombreUso.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcionUso.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStockUso.setCellValueFactory(new PropertyValueFactory<>("stock"));
        clmCostoUnitario.setCellValueFactory(new PropertyValueFactory<>("costo"));

        OrdenReparacionDAO db2 = new OrdenReparacionDAOImp();
        ArrayList<Pieza> piezas = db2.obtenerPiezasReparacion(idReparacion);
        observablePiezasUso.setAll(piezas);
        tblPiezasUso.setItems(observablePiezasUso);


        tblPiezas.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1) { // o 2 para doble clic
                Pieza seleccion = tblPiezas.getSelectionModel().getSelectedItem();

                if (seleccion != null) {
                    piezaSeleccionada = seleccion;
                }
            }
        });
    }

    public void btnUsar_click(ActionEvent actionEvent) {
        if(piezaSeleccionada == null) {
            Alerts.showAlert("Error","Seleccione una pieza a utilizar", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            return;
        }
        if(spnrCantidadUsar.getValue()>piezaSeleccionada.getStock()){
            Alerts.showAlert("Error","No se puede utilizar más piezas de las que hay en stock.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            return;
        }

        int cantidadUsar = spnrCantidadUsar.getValue();
        OrdenPiezasDAO db = new OrdenPiezasDAOImp();
        if(db.registrarPiezaUsada(idReparacion,piezaSeleccionada.getId(),cantidadUsar)){
            Alerts.showAlert("Éxito","Se ha registrado la utilización de la pieza y se redujo el stokc",Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
            piezaSeleccionada.setStock(piezaSeleccionada.getStock()-cantidadUsar);
            tblPiezas.refresh();

            Pieza pieza = new Pieza();
            pieza.setId(piezaSeleccionada.getId());
            pieza.setNombre(piezaSeleccionada.getNombre());
            pieza.setDescripcion(piezaSeleccionada.getDescripcion());
            pieza.setStock(cantidadUsar);
            pieza.setCosto(piezaSeleccionada.getCosto());
            observablePiezasUso.add(pieza);
            tblPiezasUso.refresh();
        }
        else{
            Alerts.showAlert("Error","No se pudo registrar la pieza como utilizada. Intente de nuevo.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
        }

    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparar.fxml"));
            loader.setControllerFactory(p-> new RepararController(idReparacion));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }
            if(controlador instanceof RepararController){
                ((RepararController) controlador).setIdReparacion(idReparacion);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
