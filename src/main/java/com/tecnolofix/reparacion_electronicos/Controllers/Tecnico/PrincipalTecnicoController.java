package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalTecnicoController implements Initializable {
    @FXML Button btnSalir;
    @FXML VBox vboxBotones;
    @FXML ToggleButton btnPiezas;
    @FXML ToggleButton btnHerramientas;
    @FXML ToggleButton btnReparaciones;
    @FXML ToggleGroup toggleMenu;
    @FXML public BorderPane rootPane;

    public static Tecnico sesionTecnico;

    public PrincipalTecnicoController(Tecnico sesionTecnico) {
        this.sesionTecnico = sesionTecnico;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Parent vistaCentro = null;
//        try {
//            vistaCentro = FXMLLoader.load(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparaciones.fxml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparaciones.fxml"));
        loader.setControllerFactory(p->new ReparacionesController(rootPane));
        Parent vistaCentro = null; // Carga la vista y guarda el root
        try {
            vistaCentro = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Obtener el controlador de esa vista
//        Object controlador = loader.getController();
//        System.out.println("fuera del if de cambiar centro");
        // Si el controlador tiene un mtodo para recibir el rootPane, lo llamas:
//        if (controlador instanceof ControladorConRootPane) {
//            System.out.println("dentro del if para pasarlo");
//            ((ControladorConRootPane) controlador).setRootPane(rootPane);
//        }

        rootPane.setCenter(vistaCentro);

        // Reemplazar el centro del BorderPane con la nueva vista
        rootPane.setCenter(vistaCentro);
        rootPane.setTop(null);

        rootPane.setStyle("-fx-background-color: #2c3e50");
        vboxBotones.setStyle("-fx-background-color: #bdc3c7;");
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();
            // Si el controlador tiene un mtodo para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        actualizarEstilos();
    }

    public void btnReparaciones_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparaciones.fxml"));
            loader.setControllerFactory(p->new ReparacionesController(rootPane));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();
            // Si el controlador tiene un mtodo para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        actualizarEstilos();
    }

    public void btnHerramientas_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/Herramientas.fxml");
    }

    public void btnPiezas_click(ActionEvent actionEvent) {
        cambiarCentro("/com/tecnolofix/reparacion_electronicos/Encargado/Piezas.fxml");
    }

    public void btnSalir_click(ActionEvent actionEvent) throws IOException {
        Stage stageActual = (Stage) btnSalir.getScene().getWindow();
        stageActual.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Inicio.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root); // Crea la escena antes

        Stage newStage = new Stage();
        newStage.setTitle("Dashboard");
        newStage.setScene(scene);       // Asigna la escena
        newStage.sizeToScene();         // Ajusta el tamaño DESPUÉS de setScene
        newStage.setMinWidth(1000);
        newStage.setMinHeight(500);
        newStage.show();
    }
}
