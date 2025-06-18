package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.CargableConId;
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

public class DetalleRevisionController implements Initializable, ControladorConRootPane, CargableConId{
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

    public void setId(int idRevision) {
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

        lblIdRevision.setText("ID: "+orden.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblFechaIngreso.setText("Fecha ingreso: "+orden.getFechaIng().format(formatter));
        String fechaEgreso = (orden.getFechaEg()==null)? "Sin fecha":orden.getFechaEg().toString();
        lblFechaEgreso.setText("Fecha egreso "+fechaEgreso);
        lblTipoFalla.setText("Tipo de falla: "+orden.getTipoFalla());
        lblDescripcion.setText("Descripcion"+orden.getDescripcion());
        lblEstado.setText("Estado: "+orden.getEstado());

        lblIdCliente.setText("ID: "+cliente.getId());
        lblNombreCliente.setText("Nombre: "+cliente.getNombre());
        lblTelefono.setText("Teléfono: "+cliente.getTelefono());
        lblCorreo.setText("Correo: "+cliente.getCorreo());

        lblIdDispositivo.setText("ID: "+dispositivo.getId());
        lblNombreDispositivo.setText("Nombre: "+dispositivo.getNombre());
        lblMarca.setText("Marca: "+dispositivo.getMarca());
        lblTipoDispositivo.setText("Dispositivo: "+dispositivo.getTipoDispo());
        lblObservaciones.setText("Observaciones: "+dispositivo.getObservaciones());

        lblTecnicoAsignado.setText("Técnico asignado: "+tecnico.getNombre());

        System.out.println("idcargado"+orden.getId());
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
            System.out.println("Después de cargar el controlador");
            // Si el controlador tiene un metodo para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                System.out.println("Dentro del instanceof");
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }

            if(controlador instanceof CargableConId){
                System.out.println("Dentro del instanceof cargable con id:"+orden.getId());
                ((CargableConId) controlador).setId(orden.getId());
//                ((CargableConId) controlador).cargarDatos();
            }
//            if (controlador instanceof RevisionHerramientasController) {
//                System.out.println(orden.getId());
//                ((RevisionHerramientasController) controlador).setId(orden.getId());
//                ((RevisionHerramientasController) controlador).cargarDatos();
//            }

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
