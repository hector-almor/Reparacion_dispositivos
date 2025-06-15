package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

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

public class PrincipalTecnicoController implements Initializable {
    @FXML ToggleButton btnPiezas;
    @FXML ToggleButton btnHerramientas;
    @FXML ToggleButton btnReparaciones;
    @FXML ToggleGroup toggleMenu;
    @FXML ToggleButton btnRevisiones;
    @FXML BorderPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent vistaCentro = null;
        try {
            vistaCentro = FXMLLoader.load(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnicosd/Revisiones.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Reemplazar el centro del BorderPane con la nueva vista
        rootPane.setCenter(vistaCentro);
        rootPane.setTop(null);
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

    public void btnRevisiones_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Tecnico/Revisiones.fxml");
    }

    public void btnReparaciones_click(ActionEvent actionEvent) {
    }

    public void btnHerramientas_click(ActionEvent actionEvent) {
    }

    public void btnPiezas_click(ActionEvent actionEvent) {
    }

}
