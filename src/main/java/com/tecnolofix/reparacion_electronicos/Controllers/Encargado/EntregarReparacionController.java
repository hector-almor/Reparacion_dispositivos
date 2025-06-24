package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Garantia;
import com.tecnolofix.reparacion_electronicos.Models.PdfGarantia;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

    public EntregarReparacionController(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 3);
        spnrDuracion.setValueFactory(valueFactory);
        spnrDuracion.setEditable(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = LocalDate.now().format(formatter);
        lblFechaInicio.setText(lblFechaInicio.getText()+" "+fechaFormateada);

        LocalDate fechaActual = LocalDate.now();
        int mesesASumar = spnrDuracion.getValue();
        LocalDate fechaFinal = fechaActual.plusMonths(mesesASumar);
        String fechaFinalFormateada = fechaFinal.format(formatter);

        lblFechaFinalizacion.setText(lblFechaFinalizacion.getText()+" "+fechaFinalFormateada);

        spnrDuracion.valueProperty().addListener((obs, oldValue, newValue) -> {
            LocalDate nuevaFecha = LocalDate.now().plusMonths(newValue);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblFechaFinalizacion.setText("Fecha de finalización: " + nuevaFecha.format(formatter2));
        });
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/DetalleReparacion.fxml"));
            loader.setControllerFactory(p->new DetalleReparacionController(idReparacion));
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

    public void btnFinalizar_click(ActionEvent actionEvent) {
        Garantia garantia = new Garantia();
        String cobertura = txtCobertura.getText().replace('\n',' ');
        garantia.setCobertura(cobertura);
        garantia.setFechaInicio(LocalDate.now());
        garantia.setDuracion(spnrDuracion.getValue());

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.entregarReparacion(idReparacion, garantia)) {
            Alerts.showAlert("Éxito","Se ha marcado como entregada la reparación y se ha generado la garantía", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        PdfGarantia.generarGarantia(idReparacion);
                        Platform.runLater(() -> Alerts.showAlert(
                                "Éxito",
                                "PDF generado correctamente en el directorio actual.",
                                Alert.AlertType.INFORMATION,
                                new ButtonType[]{ButtonType.OK}
                        ));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> Alerts.showAlert(
                                "Error",
                                "Ocurrió un error al generar el PDF:\n" + e.getMessage(),
                                Alert.AlertType.ERROR,
                                new ButtonType[]{ButtonType.OK}
                        ));
                    }
                    return null;
                }
            };

            new Thread(task).start();
        }
        else {
            Alerts.showAlert("Error","Ha habido un error al generar la garantía y marcar la reparación como entregada", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
        }
    }


}
