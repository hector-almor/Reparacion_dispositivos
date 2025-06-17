package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Garantia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EntregarReparacionController implements Initializable, ControladorConRootPane {
    @FXML Button btnFinalizar;
    @FXML TextArea txtCobertura;
    @FXML Label lblFechaFinalizacion;
    @FXML Spinner<Integer> spnrDuracion;
    @FXML Label lblFechaInicio;
    @FXML Button btnRegresar;

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
        spnrDuracion = new Spinner<>(1,24,3);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = LocalDate.now().format(formatter);
        lblFechaInicio.setText(lblFechaInicio.getText()+" "+fechaFormateada);

        LocalDate fechaActual = LocalDate.now();
        int diasASumar = spnrDuracion.getValue();
        LocalDate fechaFinal = fechaActual.plusDays(diasASumar);
        String fechaFinalFormateada = fechaFinal.format(formatter);

        lblFechaFinalizacion.setText(lblFechaFinalizacion.getText()+" "+fechaFinalFormateada);

        spnrDuracion.valueProperty().addListener((obs, oldValue, newValue) -> {
            LocalDate nuevaFecha = LocalDate.now().plusDays(newValue);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblFechaInicio.setText("Fecha de finalización: " + nuevaFecha.format(formatter2));
        });
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/DetalleReparacion.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if (controlador instanceof DetalleReparacionController) {
                ((DetalleReparacionController) controlador).setIdReparacion(idReparacion);
            }
            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnFinalizar_click(ActionEvent actionEvent) {
        Garantia garantia = new Garantia();
        String cobertura = txtCobertura.getText().replace('\n',' ');
        garantia.setCobertura(cobertura);
        garantia.setFechaInicio(LocalDate.now());
        garantia.setDuracion(spnrDuracion.getValue());

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.entregarReparacion(idReparacion, garantia)) {
            Alerts.showAlert("Éxito","Se ha marcado como entregada la reparación y se ha generado la garantía", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
        }
        else {
            Alerts.showAlert("Error","Ha habido un error al generar la garantía y marcar la reparación como entregada", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
        }
    }


}
