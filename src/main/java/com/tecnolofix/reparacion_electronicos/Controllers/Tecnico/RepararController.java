package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RepararController implements Initializable, ControladorConRootPane {
    @FXML Button btnGuardarCambios;
    @FXML Button btnUsarPiezas;
    @FXML Button btnIniciarReparacion;
    @FXML TextArea txtDescripcion;
    @FXML Button btnRegresar;
    @FXML Button btnCompletado;
    @FXML Button btnUsarHerramientas;
    @FXML Label lblObservaciones;
    @FXML Label lblTipoDispositivo;
    @FXML Label lblMarca;
    @FXML Label lblNombreDispositivo;
    @FXML Label lblIdDispositivo;
    @FXML Label lblEstado;
    @FXML Label lblTipoFalla;
    @FXML Label lblFechaIngreso;
    @FXML Label lblIdRevision;

    public static ArrayList<Pieza> piezasEnUso;
    public static ArrayList<Herramienta> herramientasEnUso;

    private int idReparacion;
    private BorderPane rootPane;
    private OrdenReparacion orden;
    private Dispositivo dispositivo;

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }
    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public RepararController(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        OrdenCompleta ordenCompleta = db.obtenerReparacionCompleta(idReparacion);
        var dispositivo = new Dispositivo();
        dispositivo.setId(ordenCompleta.getIdDispositivo());
        dispositivo.setNombre(ordenCompleta.getNombreDispositivo());
        dispositivo.setMarca(ordenCompleta.getMarcaDispositivo());
        dispositivo.setTipoDispo(ordenCompleta.getTipoDispositivo());
        dispositivo.setObservaciones(ordenCompleta.getObservacionesDispositivo());
        this.dispositivo = dispositivo;
        var orden = new OrdenReparacion();
        orden.setId(ordenCompleta.getId());
        orden.setFechaIng(ordenCompleta.getFechaIng());
        orden.setFechaEg(ordenCompleta.getFechaEg());
        orden.setTipoFalla(ordenCompleta.getTipoFalla());
        orden.setDescripcion(ordenCompleta.getDescripcion());
        orden.setTipoOrden(ordenCompleta.getTipoOrden());
        orden.setEstado(ordenCompleta.getEstado());

        lblIdRevision.setText(lblIdRevision.getText()+" "+orden.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblFechaIngreso.setText(lblFechaIngreso.getText()+" "+formatter.format(orden.getFechaIng()));
        lblTipoFalla.setText(lblTipoFalla.getText()+" "+orden.getTipoFalla());
        lblEstado.setText(lblEstado.getText()+" "+orden.getEstado());
        txtDescripcion.setText(orden.getDescripcion());

        lblIdDispositivo.setText(lblIdDispositivo.getText()+" "+dispositivo.getId());
        lblNombreDispositivo.setText(lblNombreDispositivo.getText()+" "+dispositivo.getNombre());
        lblMarca.setText(lblMarca.getText()+" "+dispositivo.getMarca());
        lblTipoDispositivo.setText(lblTipoDispositivo.getText()+" "+dispositivo.getTipoDispo());
        lblObservaciones.setText(lblObservaciones.getText()+" "+dispositivo.getObservaciones());
    }

    public void btnUsarHerramientas_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/UsarHerramientas.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }
            if(controlador instanceof ReparacionesController){
                ((RepararController) controlador).setIdReparacion(idReparacion);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCompletado_click(ActionEvent actionEvent) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.cambiarEstadoReparacion(idReparacion,"COMPLETADO")){
            Alerts.showAlert("Éxito","Se ha marcado la reparación como completada.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
            lblEstado.setText("Estado: COMPLETADO");
        }else{
            Alerts.showAlert("Error","No se pudo marcar la reparación como completada.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
        }
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparaciones.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

//            if(controlador instanceof ReparacionesController){
//                ((RepararController) controlador).setIdReparacion(idReparacion);
//            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnIniciarReparacion_click(ActionEvent actionEvent) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.cambiarEstadoReparacion(idReparacion,"PROGRESO")){
            Alerts.showAlert("Éxito","Se ha marcado la reparación como iniciada (PROGRESO).", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
            lblEstado.setText("Estado: PROGRESO");
        }else{
            Alerts.showAlert("Error","No se pudo marcar la reparación como iniciada.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
        }
    }

    public void btnUsarPiezas_click(ActionEvent actionEvent) {
        if(orden.getTipoOrden().name().equalsIgnoreCase("REVISION")){
            Alerts.showAlert("Error","Aún no se pueden usar piezas, a menos que el encargado autorice reparar este dispositivo.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/UsarPiezas.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }
            if(controlador instanceof ReparacionesController){
                ((RepararController) controlador).setIdReparacion(idReparacion);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnGuardarCambios_click(ActionEvent actionEvent) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.actualizarDescripcionReparacion(idReparacion,txtDescripcion.getText().replace("\n"," "))){
            Alerts.showAlert("Éxito","Se ha actualizado la descripción de la reparación.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
        }else{
            Alerts.showAlert("Error","No se pudo actualizar la descripción de la reparación.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
        }
    }
}
