package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenReparacionDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenReparacionDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.HerramientaConCantidad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderRepeat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RevisionHerramientasController implements Initializable, ControladorConRootPane {
    @FXML Button btnRegresar;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmCantidad;
    @FXML TableColumn<HerramientaConCantidad,String> clmDescripcion;
    @FXML TableColumn<HerramientaConCantidad,String> clmNombre;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmId;
    @FXML TableView<HerramientaConCantidad> tblHerramientas;

    private BorderPane rootPane;
    private int idOrden;
    ObservableList<HerramientaConCantidad> observableHerramientas = FXCollections.observableArrayList();

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setIdOrden(int idOrden) {this.idOrden = idOrden;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarDatos(){
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmCantidad.setCellValueFactory(new PropertyValueFactory<>("stockEnUso"));

        OrdenReparacionDAO db = new OrdenReparacionDAOImp();
        ArrayList<HerramientaConCantidad> herramientas = db.obtenerHerramientasConCantidad();
        observableHerramientas.setAll(herramientas);
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
            if (controlador instanceof DetalleRevisionController) {
                ((DetalleRevisionController) controlador).setIdRevision(idOrden);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
