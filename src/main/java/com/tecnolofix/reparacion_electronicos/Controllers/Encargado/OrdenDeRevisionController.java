package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Cliente;
import com.tecnolofix.reparacion_electronicos.Models.Dispositivo;
import com.tecnolofix.reparacion_electronicos.Models.OrdenReparacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrdenDeRevisionController implements Initializable {
    @FXML public TextField txtTelefono;
    @FXML ComboBox<String> cmbTipoRevision;
    @FXML Button btnRegistrar;
    @FXML TextArea txtDescripcion;
    @FXML ComboBox<String> cmbTipoFalla;
    @FXML ComboBox<String> cmbDispositivo;
    @FXML TextArea txtObservaciones;
    @FXML TextField txtMarca;
    @FXML TextField txtNombreDispositivo;
    @FXML TextField txtCorreo;
    @FXML TextField txtNombreCliente;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTipoFalla.getItems().addAll("HARDWARE", "SOFTWARE");
        cmbDispositivo.getItems().addAll("LAPTOP","PC","CELULAR","TABLET");
        cmbTipoRevision.getItems().addAll("REVISION","REPARACION");
    }

    private void limpiarCampos(){
        txtTelefono.setText("");
        txtDescripcion.setText("");
        cmbTipoFalla.getSelectionModel().clearSelection();
        cmbDispositivo.getSelectionModel().clearSelection();
        txtObservaciones.setText("");
        txtMarca.setText("");
        txtNombreCliente.setText("");
        txtCorreo.setText("");
        txtNombreDispositivo.setText("");
        txtNombreCliente.setText("");
        txtNombreDispositivo.setText("");
        txtCorreo.setText("");
        txtMarca.setText("");
    }

    public void btnRegistrar_click(ActionEvent actionEvent) {
        Cliente cliente = new Cliente();
        cliente.setNombre(txtNombreCliente.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        cliente.setCorreo(txtCorreo.getText().trim());

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNombre(txtNombreDispositivo.getText().trim());
        dispositivo.setMarca(txtMarca.getText().trim());
        dispositivo.setTipoDispo(Dispositivo.TipoDispositivo.valueOf(cmbDispositivo.getValue()));
        String txtLimpio = txtObservaciones.getText().replace("\n", " ");
        dispositivo.setObservaciones(txtLimpio);

        OrdenReparacion reparacion = new OrdenReparacion();
        reparacion.setFechaIng(LocalDate.now());
        reparacion.setFechaEg(null);
        reparacion.setTipoFalla(OrdenReparacion.TipoFalla.valueOf(cmbTipoFalla.getValue()));
        reparacion.setTipoOrden(OrdenReparacion.TipoOrden.valueOf(cmbTipoRevision.getValue()));
        String txtLimpio2 = txtObservaciones.getText().replace("\n", " ");
        reparacion.setDescripcion(txtLimpio2);
        reparacion.setEstado(OrdenReparacion.Estado.PENDIENTE);


        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        if(db.registrarRevision(reparacion,dispositivo,cliente)){
            Alerts.showAlert("Éxito","Se ha registrado la revisión correctamente", Alert.AlertType.CONFIRMATION,new ButtonType[]{ButtonType.OK});
            limpiarCampos();
        }else{
            Alerts.showAlert("Error","Hubo un error al registrar la revisión, intente de nuevo", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});

        }
    }
}
