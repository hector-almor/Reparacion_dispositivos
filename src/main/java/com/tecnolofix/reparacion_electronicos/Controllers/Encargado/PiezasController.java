package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.mysql.cj.xdevapi.DatabaseObject;
import com.tecnolofix.reparacion_electronicos.DB.DAO.PiezaDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.PiezaDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Pieza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PiezasController implements Initializable {
    @FXML TextField txtFiltro;
    @FXML TableColumn<Pieza, DatabaseObject> clmCosto;
    @FXML TableColumn<Pieza,Integer> clmStock;
    @FXML TableColumn<Pieza,String> clmDescripcion;
    @FXML TableColumn<Pieza,String> clmNNombre;
    @FXML TableColumn<Pieza,String> clmId;
    @FXML TableView<Pieza> tblPiezas;

    ObservableList<Pieza> observablePiezas = FXCollections.observableArrayList();
    FilteredList<Pieza> filteredPiezas = new FilteredList<>(observablePiezas);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        clmCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        PiezaDAO db = new PiezaDAOImp();
        ArrayList<Pieza> listaPiezas = db.getAllPiezas();
        filteredPiezas.setPredicate(p->true);
        observablePiezas.setAll(listaPiezas);
        tblPiezas.setItems(filteredPiezas);

        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPiezas.setPredicate(h -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return h.getNombre().toLowerCase().contains(newValue.toLowerCase());
            });
        });
    }
}
