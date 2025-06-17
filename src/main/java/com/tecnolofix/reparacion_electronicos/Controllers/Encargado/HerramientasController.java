package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.DB.DAO.HerramientaDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.HerramientaDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Herramienta;
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

public class HerramientasController implements Initializable {
    @FXML TextField txtFiltro;
    @FXML TableColumn<Herramienta,Integer> clmStockUso;
    @FXML TableColumn<Herramienta,Integer> clmStockDisponible;
    @FXML TableColumn<Herramienta,String> clmDescripcion;
    @FXML TableColumn<Herramienta,String> clmNombre;
    @FXML TableColumn<Herramienta,Integer> clmId;
    @FXML TableView<Herramienta> tblHerramientas;

    ObservableList<Herramienta> observableHerramientas = FXCollections.observableArrayList();
    FilteredList<Herramienta> filteredHerramientas = new FilteredList<>(observableHerramientas);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStockDisponible.setCellValueFactory(new PropertyValueFactory<>("stockDisponible"));
        clmStockUso.setCellValueFactory(new PropertyValueFactory<>("stockEnUso"));

        HerramientaDAO db = new HerramientaDAOImp();
        ArrayList<Herramienta> herramientas = db.getAllHerramientas();
        observableHerramientas.setAll(herramientas);
        filteredHerramientas.setPredicate(h->true);

        tblHerramientas.setItems(filteredHerramientas);


        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredHerramientas.setPredicate(h -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return h.getNombre().toLowerCase().contains(newValue.toLowerCase());
            });
        });
    }
}
