package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReparacionHerramientasPiezasController implements Initializable, ControladorConRootPane {
    @FXML TableView<PiezaConCantidad> tblPiezas;
    @FXML Label lblCostoTotal;
    @FXML TableColumn<PiezaConCantidad,Integer> clmCantidadPieza;
    @FXML TableColumn<PiezaConCantidad,Double> clmCosto;
    @FXML TableColumn<PiezaConCantidad,String> clmDescripcionPieza;
    @FXML TableColumn<PiezaConCantidad,String> clmNombrePieza;
    @FXML TableColumn<PiezaConCantidad,Integer> clmIdPieza;
    @FXML Button btnRegresar;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmCantidadHerramienta;
    @FXML TableColumn<HerramientaConCantidad,String> clmDescripcionHerramienta;
    @FXML TableColumn<HerramientaConCantidad,String> clmNombreHerramienta;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmIdHerramienta;
    @FXML TableView<HerramientaConCantidad> tblHerramientas;

    ObservableList<HerramientaConCantidad> observableHerramientas = FXCollections.observableArrayList();
    ObservableList<PiezaConCantidad> observablePiezas = FXCollections.observableArrayList();

    private BorderPane rootPane;
    private int idReparacion;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clmIdHerramienta.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombreHerramienta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcionHerramienta.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmCantidadHerramienta.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        clmIdPieza.setCellValueFactory(new PropertyValueFactory<>("idPieza"));
        clmNombrePieza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcionPieza.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        clmCantidadPieza.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        HerramientasPiezasConCosto obj = db.obtenerHerramientasPiezasConCosto(idReparacion);
        ArrayList<HerramientaConCantidad> herramientas = obj.getHerramientas();
        ArrayList<PiezaConCantidad> piezas = obj.getPiezas();
        double totalPiezas = obj.getCostoTotalPiezas();

        observableHerramientas.addAll(herramientas);
        observablePiezas.addAll(piezas);

        tblHerramientas.setItems(observableHerramientas);
        tblPiezas.setItems(observablePiezas);
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/Reparaciones.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if(controlador instanceof DetalleReparacionController){
                ((DetalleReparacionController)controlador).setIdReparacion(idReparacion);
            }
            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
