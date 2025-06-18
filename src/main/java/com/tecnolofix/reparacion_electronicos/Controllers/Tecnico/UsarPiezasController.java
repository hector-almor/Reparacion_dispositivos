package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsarPiezasController implements Initializable, ControladorConRootPane {
    @FXML Button btnRegresar;
    @FXML Spinner spnrDevolver;
    @FXML Button btnDevolver;
    @FXML TableColumn clmStockUso;
    @FXML TableColumn clmDescripcionUso;
    @FXML TableColumn clmNNombreUso;
    @FXML TableColumn clmIdUso;
    @FXML TableView tblPiezasUso;
    @FXML Spinner spnrCantidadUsar;
    @FXML Button btnUsar;
    @FXML TextField txtFiltro;
    @FXML TableColumn clmCosto;
    @FXML TableColumn clmStock;
    @FXML TableColumn clmDescripcion;
    @FXML TableColumn clmNombre;
    @FXML TableColumn clmId;
    @FXML TableView tblPiezas;

    private int idReparacion;
    private BorderPane rootPane;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setIdOrden(int idOrden) {
        this.idReparacion = idOrden;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnUsar_click(ActionEvent actionEvent) {
    }

    public void btnDevolver_click(ActionEvent actionEvent) {
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparar.fxml"));
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
