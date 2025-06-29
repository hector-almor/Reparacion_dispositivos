package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.PiezaDAO;
import com.tecnolofix.reparacion_electronicos.DB.DAO.ProveedorDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.PiezaDAOImp;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.ProveedorDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Pieza;
import com.tecnolofix.reparacion_electronicos.Models.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ComprarPiezasController implements Initializable, ControladorConRootPane {
    @FXML Button btnNuevoProveedor;
    @FXML TextField txtNombre;
    @FXML Button btnRegistrar;
    @FXML TableColumn<Pieza,Integer> clmCantidad;
    @FXML TableColumn<Pieza,Double> clmCosto;
    @FXML TableColumn<Pieza,String> clmDescripcion;
    @FXML TableColumn<Pieza,String> clmNombre;
    @FXML TableColumn<Pieza,String> clmId;
    @FXML TableView<Pieza> tblPiezas;
    @FXML Button btnAgregar;
    @FXML TextField txtCantidad;
    @FXML TextField txtCosto;
    @FXML TextField txtDescripcion;
    @FXML TextField txtId;
    @FXML ComboBox<Proveedor> cmbProveedor;

    ArrayList<Proveedor> proveedores = new ArrayList<>();
    ObservableList<Pieza> observablePiezas = FXCollections.observableArrayList();

    ArrayList<Pieza> piezasBD = new ArrayList<>();
    ArrayList<Pieza> piezasBDUpdate = new ArrayList<>();
    ArrayList<Pieza> piezasBDAdd = new ArrayList<>();
    Map<String,Pieza> diccionarioPiezas;


    private BorderPane rootPane;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProveedorDAO db = new ProveedorDAOImp();
        proveedores = db.getAllProveedores();
        cmbProveedor.getItems().addAll(proveedores);

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        clmCantidad.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tblPiezas.setItems(observablePiezas);

        PiezaDAO dbPiezas = new PiezaDAOImp();
        piezasBD = dbPiezas.getAllPiezas();

        diccionarioPiezas = new HashMap<>();
        for(Pieza pieza : piezasBD ){
            diccionarioPiezas.put(pieza.getId(),pieza);
        }


        txtId.textProperty().addListener((observable, oldValue, newValue) -> {
            if(diccionarioPiezas.containsKey(txtId.getText())){
                txtCantidad.requestFocus();
                txtDescripcion.setText(diccionarioPiezas.get(txtId.getText()).getDescripcion());
                txtNombre.setText(diccionarioPiezas.get(txtId.getText()).getNombre());
                txtCosto.setText(String.valueOf(diccionarioPiezas.get(txtId.getText()).getCosto()));
            }
        });

    }

    public void btnAgregar_click(ActionEvent actionEvent) {
        Pieza newPieza = new Pieza();
        newPieza.setId(txtId.getText().trim());
        newPieza.setNombre(txtNombre.getText().trim());
        newPieza.setDescripcion(txtDescripcion.getText().trim());
        newPieza.setCosto(Double.parseDouble(txtCosto.getText().trim()));
        newPieza.setStock(Integer.parseInt(txtCantidad.getText().trim()));
        observablePiezas.add(newPieza);

        if(diccionarioPiezas.containsKey(newPieza.getId())){
            piezasBDUpdate.add(newPieza);
        }else{
            piezasBDAdd.add(newPieza);
        }

        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCosto.setText("");
        txtCantidad.setText("");
    }

    public void btnRegistrar_click(ActionEvent actionEvent) {
        if(cmbProveedor.getSelectionModel().getSelectedItem() == null){
            Alerts.showAlert("Error","Seleccione un proveedor antes de registrar la compra.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            return;
        }

        PiezaDAO db  = new PiezaDAOImp();
        if(db.registrarCompras(piezasBDAdd,piezasBDUpdate,new ArrayList<>(observablePiezas),cmbProveedor.getSelectionModel().getSelectedItem().getId())){
            Alerts.showAlert("Éxito","Se registró la compra correctamente.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
            observablePiezas.clear();
            cmbProveedor.getSelectionModel().clearSelection();
        }else{
            Alerts.showAlert("Error","No se pudo registrar la compra. Intente de nuevo", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
        }
    }

    public void btnNuevoProveedor_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/RegistrarProveedores.fxml"));
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
