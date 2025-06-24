package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.PiezaDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.PiezaDAOImp;
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
    @FXML Spinner<Integer> spnrDevolver;
    @FXML Button btnDevolver;
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
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        clmCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        PiezaDAO db = new PiezaDAOImp();
        ArrayList<Pieza> listaPiezas = db.getAllPiezas();
        observablePiezas.setAll(listaPiezas);
        tblPiezas.setItems(filteredPiezas);
        filteredPiezas.setPredicate(p->true);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPiezas.setPredicate(h -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return h.getNombre().toLowerCase().contains(newValue.toLowerCase());
            });
        });
    }

    public void btnUsar_click(ActionEvent actionEvent) {
    }

    public void btnDevolver_click(ActionEvent actionEvent) {
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
