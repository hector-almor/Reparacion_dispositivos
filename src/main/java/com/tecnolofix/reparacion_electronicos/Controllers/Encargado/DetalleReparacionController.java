package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.CargableConId;
import com.tecnolofix.reparacion_electronicos.Controllers.Contexto;
import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetalleReparacionController implements Initializable, ControladorConRootPane, CargableConId {
    @FXML Button btnEntregar;
    @FXML Button btnRegresar;
    @FXML Button btnGarantia;
    @FXML Button btnHerramientasPiezas;
    @FXML Label lblTecnicoAsignado;
    @FXML Label lblObservaciones;
    @FXML Label lblTipoDispositivo;
    @FXML Label lblMarca;
    @FXML Label lblNombreDispositivo;
    @FXML Label lblIdDispositivo;
    @FXML Label lblCorreo;
    @FXML Label lblTelefono;
    @FXML Label lblNombreCliente;
    @FXML Label lblIdCliente;
    @FXML Label lblEstado;
    @FXML Label lblDescripcion;
    @FXML Label lblTipoFalla;
    @FXML Label lblFechaEgreso;
    @FXML Label lblFechaIngreso;
    @FXML Label lblIdRevision;

    private BorderPane rootPane;
    private int idReparacion;
    private OrdenReparacion orden;
    private Cliente cliente;
    private Dispositivo dispositivo;
    private Tecnico tecnico;
    private Garantia garantia;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setId(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    public DetalleReparacionController(int idReparacion){
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        OrdenCompleta ordenCompleta = db.obtenerReparacionCompleta(idReparacion);
        this.cliente = ordenCompleta.getCliente();
        this.tecnico = ordenCompleta.getTecnico();
        dispositivo = new Dispositivo();
        dispositivo.setId(ordenCompleta.getIdDispositivo());
        dispositivo.setNombre(ordenCompleta.getNombreDispositivo());
        dispositivo.setMarca(ordenCompleta.getMarcaDispositivo());
        dispositivo.setTipoDispo(ordenCompleta.getTipoDispositivo());
        dispositivo.setObservaciones(ordenCompleta.getObservacionesDispositivo());
        orden = new OrdenReparacion();
        orden.setId(ordenCompleta.getId());
        orden.setFechaIng(ordenCompleta.getFechaIng());
        orden.setFechaEg(ordenCompleta.getFechaEg());
        orden.setTipoFalla(ordenCompleta.getTipoFalla());
        orden.setDescripcion(ordenCompleta.getDescripcion());
        orden.setTipoOrden(ordenCompleta.getTipoOrden());
        orden.setEstado(ordenCompleta.getEstado());

        lblIdRevision.setText(lblIdRevision.getText()+" "+orden.getId());
        lblFechaIngreso.setText(lblFechaIngreso.getText()+" "+orden.getFechaIng());
        String fechaEgreso = (orden.getFechaEg()==null)? "Sin fecha":orden.getFechaEg().toString();
        lblFechaEgreso.setText(lblFechaEgreso.getText()+" "+fechaEgreso);
        lblTipoFalla.setText(lblTipoFalla.getText()+" "+orden.getTipoFalla());
        lblDescripcion.setText(lblDescripcion.getText()+" "+orden.getDescripcion());
        lblEstado.setText(lblEstado.getText()+" "+orden.getEstado());

        lblIdCliente.setText(lblIdCliente+" "+cliente.getId());
        lblNombreCliente.setText(lblNombreCliente+" "+cliente.getNombre());
        lblTelefono.setText(lblTelefono.getText()+" "+cliente.getTelefono());
        lblCorreo.setText(lblCorreo.getText()+" "+cliente.getCorreo());

        lblIdDispositivo.setText(lblIdDispositivo.getText()+" "+dispositivo.getId());
        lblNombreDispositivo.setText(lblNombreDispositivo.getText()+" "+dispositivo.getNombre());
        lblMarca.setText(lblMarca.getText()+" "+dispositivo.getMarca());
        lblTipoDispositivo.setText(lblTipoDispositivo.getText()+" "+dispositivo.getTipoDispo());
        lblObservaciones.setText(lblObservaciones.getText()+" "+dispositivo.getObservaciones());

        lblTecnicoAsignado.setText(lblTecnicoAsignado.getText()+" "+tecnico.getNombre());

        garantia = ordenCompleta.getGarantia();
    }

    @Override
    public void cargarDatos() {

    }

    public void btnHerramientasPiezas_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/ReparacionHerramientasPiezas.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if(controlador instanceof ReparacionHerramientasPiezasController){
                ((ReparacionHerramientasPiezasController) controlador).setIdReparacion(orden.getId());
            }
            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnGarantia_click(ActionEvent actionEvent) {
        if(orden.getFkGarantia()==-1){
            Alerts.showAlert("Aviso","Esta orden de reparación aún no tiene garantía", Alert.AlertType.CONFIRMATION,new ButtonType[]{ButtonType.OK});
            return;
        }

        String txtGarantia = """
                            Garantía
                Fecha de inicio: %s
                Duración: %s
                Fecha de fin: %s
                Cobertura: %s
                """.formatted(garantia.getFechaInicio(),garantia.getDuracion(),garantia.getFechaFin(),garantia.getCobertura());
        Alerts.showAlert("Detalle de garantía",txtGarantia,Alert.AlertType.CONFIRMATION,new ButtonType[]{ButtonType.OK});
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

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnEntregar_click(ActionEvent actionEvent) {
        if(!orden.getEstado().name().equalsIgnoreCase("COMPLETADO")){
            Alerts.showAlert("Error","No se puede entregar una orden de reparación aún no completada.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/Reparaciones.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if(controlador instanceof EntregarReparacionController){
                ((EntregarReparacionController) controlador).setIdReparacion(idReparacion);
            }
            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
