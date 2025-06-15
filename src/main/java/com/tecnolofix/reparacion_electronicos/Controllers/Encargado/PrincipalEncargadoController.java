package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalEncargadoController implements Initializable {
    @FXML public ToggleButton btnRevisiones;
    @FXML ToggleButton btnTecnicos;
    @FXML ToggleButton btnComprarPiezas;
    @FXML ToggleButton btnPiezas;
    @FXML ToggleButton btnHerramientas;
    @FXML ToggleButton btnAsignacionActividades;
    @FXML ToggleButton btnRefacciones;
    @FXML ToggleButton btnReparaciones;
//    @FXML ToggleButton btnOrdenReparacion;
    @FXML ToggleButton btnOrdenRevision;
    @FXML BorderPane rootPane;
    @FXML ToggleGroup toggleMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent vistaCentro = null;
        try {
            vistaCentro = FXMLLoader.load(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/OrdenDeRevision.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Reemplazar el centro del BorderPane con la nueva vista
        rootPane.setCenter(vistaCentro);
        rootPane.setTop(null);

//        btnRefacciones.setOnAction(event -> actualizarEstilos());
//        btnReparaciones.setOnAction(event -> actualizarEstilos());
//        btnAsignacionTareas.setOnAction(event -> actualizarEstilos());
//        btnOrdenReparacion.setOnAction(event -> actualizarEstilos());
//        btnOrdenRevision.setOnAction(event -> actualizarEstilos());
    }

    private void actualizarEstilos() {
        for (Toggle toggle : toggleMenu.getToggles()) {
            if (toggle instanceof ToggleButton toggleButton) {
                if (toggleButton.isSelected()) {
                    toggleButton.setStyle("-fx-background-color: #117a8b; -fx-border-color: #0f6674; -fx-text-fill: white;");
                } else {
                    toggleButton.setStyle(""); // Restaurar estilo por defecto
                }
            }
        }
    }

    private void cambiarCentro(String rutaFxml){
        Parent vistaCentro = null;
        try {
            vistaCentro = FXMLLoader.load(getClass().getResource(rutaFxml));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rootPane.setCenter(vistaCentro);
        actualizarEstilos();
    }

    public void btnOrdenRevision_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/OrdenDeRevision.fxml");
    }

//    public void btnOrdenReparacion_click(ActionEvent actionEvent) {
//        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/OrdenReparacion.fxml");
//    }

    public void btnAsignacionActividades_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/AsignacionActividades.fxml");
    }

    public void btnReparaciones_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/Reparaciones.fxml");
    }

    public void btnRevisiones_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/Revisiones.fxml");
    }

    public void btnHerramientas_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/Herramientas.fxml");
    }

    public void btnPiezas_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/Piezas.fxml");
    }

    public void btnComprarPiezas_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/ComprarPiezas.fxml");
    }

    public void btnTecnicos_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/Tecnicos.fxml");
    }
}
