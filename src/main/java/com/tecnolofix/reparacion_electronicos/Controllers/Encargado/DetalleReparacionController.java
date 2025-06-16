package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DetalleReparacionController implements Initializable, ControladorConRootPane {
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

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        OrdenCompleta ordenCompleta = db.obtenerRevisionCompleta(idReparacion);
        this.cliente = ordenCompleta.getCliente();
        this.tecnico = ordenCompleta.getTecnico();
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
    }

    public void btnHerramientasPiezas_click(ActionEvent actionEvent) {

    }

    public void btnGarantia_click(ActionEvent actionEvent) {
        if(orden.getFkGarantia()==-1){
        }
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
    }

    public void btnEntregar_click(ActionEvent actionEvent) {

    }
}
