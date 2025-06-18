package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class DetalleRevisionController implements Initializable, ControladorConRootPane {
    @FXML Button btnRegresar;
    @FXML Button btnReparar;
    @FXML Button btnHerramientas;
    @FXML Button btnFinalizar;
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
    private int idRevision;
    private OrdenReparacion orden;
    private Cliente cliente;
    private Dispositivo dispositivo;
    private Tecnico tecnico;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setIdRevision(int idRevision) {
        this.idRevision = idRevision;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarDatos(){
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        OrdenCompleta ordenCompleta = db.obtenerRevisionCompleta(idRevision);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblFechaIngreso.setText(lblFechaIngreso.getText()+" "+orden.getFechaIng().format(formatter));
        String fechaEgreso = (orden.getFechaEg()==null)? "Sin fecha":orden.getFechaEg().toString();
        lblFechaEgreso.setText(lblFechaEgreso.getText()+" "+fechaEgreso);
        lblTipoFalla.setText(lblTipoFalla.getText()+" "+orden.getTipoFalla());
        lblDescripcion.setText(lblDescripcion.getText()+" "+orden.getDescripcion());
        lblEstado.setText(lblEstado.getText()+" "+orden.getEstado());

        lblIdCliente.setText(lblIdCliente.getText()+" "+cliente.getId());
        lblNombreCliente.setText(lblNombreCliente.getText()+" "+cliente.getNombre());
        lblTelefono.setText(lblTelefono.getText()+" "+cliente.getTelefono());
        lblCorreo.setText(lblCorreo.getText()+" "+cliente.getCorreo());

        lblIdDispositivo.setText(lblIdDispositivo.getText()+" "+dispositivo.getId());
        lblNombreDispositivo.setText(lblNombreDispositivo.getText()+" "+dispositivo.getNombre());
        lblMarca.setText(lblMarca.getText()+" "+dispositivo.getMarca());
        lblTipoDispositivo.setText(lblTipoDispositivo.getText()+" "+dispositivo.getTipoDispo());
        lblObservaciones.setText(lblObservaciones.getText()+" "+dispositivo.getObservaciones());

        lblTecnicoAsignado.setText(lblTecnicoAsignado.getText()+" "+tecnico.getNombre());
    }

    public void btnFinalizar_click(ActionEvent actionEvent) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.cancelarRevision(orden.getId(),LocalDate.now())){
            Alerts.showAlert("Éxito","Se ha marcado la reparación como cancelada.", Alert.AlertType.CONFIRMATION,new ButtonType[]{ButtonType.OK});
            lblEstado.setText("Estado: CANCELADO");
            lblFechaEgreso.setText("Fecha de egreso: "+LocalDate.now());
        }else{
            Alerts.showAlert("Error","Algo falló al cancelar la reparación, intentar de nuevo.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
        }
    }

    public void btnHerramientas_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/RevisionHerramientas.fxml"));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un metodo para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if (controlador instanceof RevisionHerramientasController) {
                System.out.println(orden.getId());
                ((RevisionHerramientasController) controlador).setIdOrden(orden.getId());
                ((RevisionHerramientasController) controlador).cargarDatos();
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReparar_click(ActionEvent actionEvent) {
        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.marcarParaReparacion(orden.getId())){
            Alerts.showAlert("Éxito","Se ha marcado la revisión ahora como reparación.",Alert.AlertType.CONFIRMATION,new ButtonType[]{ButtonType.OK});
            lblEstado.setText("Estado: ASIGNADO");
        }
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/Revisiones.fxml"));
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

}
