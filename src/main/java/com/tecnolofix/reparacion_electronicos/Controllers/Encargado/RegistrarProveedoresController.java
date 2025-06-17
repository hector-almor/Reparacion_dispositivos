package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.ProveedorDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.ProveedorDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Proveedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RegistrarProveedoresController implements ControladorConRootPane{
    @FXML Button btnRegresar;
    @FXML TextField txtNombre;
    @FXML Button btnRegistrar;
    @FXML TextField txtTelefono;
    @FXML TextField txtCorreo;
    @FXML TextField txtDireccion;

    private BorderPane rootPane;

    public void btnRegistrar_click(ActionEvent actionEvent) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(txtNombre.getText().trim());
        proveedor.setTelefono(txtTelefono.getText().trim());
        proveedor.setCorreo(txtCorreo.getText().trim());
        proveedor.setDireccion(txtDireccion.getText().trim());

        ProveedorDAO db = new ProveedorDAOImp();
        if(db.registrarProveedor(proveedor)) {
            Alerts.showAlert("Éxito","Se ha registrado correctamente al proveedor.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
            txtNombre.setText("");
            txtTelefono.setText("");
            txtCorreo.setText("");
            txtDireccion.setText("");
        }else{
            Alerts.showAlert("Error","No se ha podido registrar al proveedor, intente de nuevo.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
        }
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/ComprarPiezas.fxml"));
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

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }
}
