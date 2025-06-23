package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.Models.Garantia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GarantiaController implements Initializable, ControladorConRootPane {
    @FXML Button btnImprimir;
    @FXML Label lblCobertura;
    @FXML Label lblDuracion;
    @FXML Label lblFechaFinalizacion;
    @FXML Label lblFechaInicio;
    @FXML Label lblIdGarantia;
    @FXML Button btnRegresar;

    private BorderPane rootPane;
    private Garantia garantia;
    private int idReparacion;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public GarantiaController(Garantia garantia, int idReparacion){
        this.garantia = garantia;
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblIdGarantia.setText(lblIdGarantia.getText()+" "+garantia.getId());
        lblFechaInicio.setText(lblFechaInicio.getText()+" "+garantia.getFechaInicio().format(formatter));
        lblDuracion.setText(lblDuracion.getText()+" "+garantia.getDuracion()+" meses");
        lblFechaFinalizacion.setText(lblFechaFinalizacion.getText()+" "+garantia.getFechaFin().format(formatter));
        lblCobertura.setText(lblCobertura.getText()+" "+garantia.getCobertura());
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/DetalleReparacion.fxml"));
            loader.setControllerFactory(param-> new DetalleReparacionController(idReparacion));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnImprimir_click(ActionEvent actionEvent) {
    }
}
