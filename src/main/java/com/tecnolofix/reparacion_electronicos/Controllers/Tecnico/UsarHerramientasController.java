package com.tecnolofix.reparacion_electronicos.Controllers.Tecnico;

import com.tecnolofix.reparacion_electronicos.Controllers.ControladorConRootPane;
import com.tecnolofix.reparacion_electronicos.DB.DAO.HerramientaDAO;
import com.tecnolofix.reparacion_electronicos.DB.DAO.OrdenHerramientasDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.HerramientaDAOImp;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.OrdenHerramientasDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Herramienta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsarHerramientasController implements Initializable, ControladorConRootPane{

    @FXML Button btnRegresar;
    @FXML Spinner<Integer> spnrDevolver;
    @FXML Button btnDevolver;
    @FXML TableColumn<Herramienta,Integer> clmCantidadUso;
    @FXML TableColumn<Herramienta,String> clmDescripcionUso;
    @FXML TableColumn<Herramienta,String> clmNombreUso;
    @FXML TableColumn<Herramienta,Integer> clmIdUso;
    @FXML TableView<Herramienta> tblHerramientasUso;
    @FXML Spinner<Integer> spnrCantidadUsar;
    @FXML Button btnUsar;
    @FXML TextField txtFiltro;
    @FXML TableColumn<Herramienta,Integer> clmStockUso;
    @FXML TableColumn<Herramienta,Integer> clmStockDisponible;
    @FXML TableColumn<Herramienta,String> clmDescripcion;
    @FXML TableColumn<Herramienta,String> clmNombre;
    @FXML TableColumn<Herramienta,Integer> clmId;
    @FXML TableView<Herramienta> tblHerramientas;

    ObservableList<Herramienta> observableHerramientas = FXCollections.observableArrayList();
    FilteredList<Herramienta> filteredHerramientas = new FilteredList<>(observableHerramientas);

    ObservableList<Herramienta> observableHerramientasUso = FXCollections.observableArrayList();

    Herramienta herramientaSeleccionada = null;
    Herramienta herramientaEnUsoSelecionada = null;

    private BorderPane rootPane;
    private int idReparacion;

    @Override
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    public UsarHerramientasController(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        spnrCantidadUsar.setValueFactory(valueFactory);
        spnrCantidadUsar.setEditable(true);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        spnrDevolver.setValueFactory(valueFactory2);
        spnrDevolver.setEditable(true);

        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmStockDisponible.setCellValueFactory(new PropertyValueFactory<>("stockDisponible"));
        clmStockUso.setCellValueFactory(new PropertyValueFactory<>("stockEnUso"));

        HerramientaDAO db = new HerramientaDAOImp();
        ArrayList<Herramienta> herramientas = db.getAllHerramientas();
        observableHerramientas.setAll(herramientas);
        filteredHerramientas.setPredicate(h->true);

        tblHerramientas.setItems(filteredHerramientas);


        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredHerramientas.setPredicate(h -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return h.getNombre().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        clmIdUso.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombreUso.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmDescripcionUso.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        clmCantidadUso.setCellValueFactory(new PropertyValueFactory<>("stockEnUso"));

        if(RepararController.herramientasEnUso!=null){
            observableHerramientasUso.setAll(RepararController.herramientasEnUso);
            tblHerramientasUso.setItems(observableHerramientasUso);
        }

        tblHerramientas.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1) { // o 2 para doble clic
                Herramienta seleccion = tblHerramientas.getSelectionModel().getSelectedItem();

                if (seleccion != null) {
                    herramientaSeleccionada = seleccion;
                }
            }
        });

        tblHerramientasUso.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 1) { // o 2 para doble clic
                Herramienta seleccion = tblHerramientasUso.getSelectionModel().getSelectedItem();

                if (seleccion != null) {
                    herramientaEnUsoSelecionada  = seleccion;
                }
            }
        });

    }

    public void btnUsar_click(ActionEvent actionEvent) {
        if(herramientaSeleccionada!=null){
            if (spnrCantidadUsar.getValue() > herramientaSeleccionada.getStockDisponible()) {
                Alerts.showAlert("Advertencia", "No hay suficiente stock disponible.", Alert.AlertType.WARNING, new ButtonType[]{ButtonType.OK});
                return;
            }

            herramientaSeleccionada.setStockDisponible(herramientaSeleccionada.getStockDisponible()-spnrCantidadUsar.getValue());
            herramientaSeleccionada.setStockEnUso(herramientaSeleccionada.getStockEnUso()+spnrCantidadUsar.getValue());

            HerramientaDAO db = new HerramientaDAOImp();
            boolean consulta1 = db.actualizarStockDisponible(herramientaSeleccionada.getId(),Math.negateExact(spnrCantidadUsar.getValue()),spnrCantidadUsar.getValue());

            OrdenHerramientasDAO db2 = new OrdenHerramientasDAOImp();
            boolean consulta2 = db2.registrarHerramientaUsada(idReparacion,herramientaSeleccionada.getId(),spnrCantidadUsar.getValue());

            if(consulta1&&consulta2){
                Alerts.showAlert("Éxito","La herramienta se ha marcado en uso y se ha actualizado la base de datos.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
            }else{
                Alerts.showAlert("Error","No se ha podido marcar la herramienta en uso. Intente de nuevo.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            }

            Herramienta herramientaUsada = new Herramienta();
            herramientaUsada.setId(herramientaSeleccionada.getId());
            herramientaUsada.setNombre(herramientaSeleccionada.getNombre());
            herramientaUsada.setDescripcion(herramientaSeleccionada.getDescripcion());
            herramientaUsada.setStockEnUso(spnrCantidadUsar.getValue());

            observableHerramientasUso.add(herramientaUsada);
            tblHerramientasUso.setItems(observableHerramientasUso);
            RepararController.herramientasEnUso = new ArrayList<>(observableHerramientasUso);
            tblHerramientas.refresh();
            tblHerramientasUso.refresh();
        }
    }


    public void btnDevolver_click(ActionEvent actionEvent) {
        if(herramientaEnUsoSelecionada!=null){
            if (spnrDevolver.getValue() > herramientaEnUsoSelecionada.getStockEnUso()) {
                Alerts.showAlert("Advertencia", "No se puede devolver más de lo que se está usando.", Alert.AlertType.WARNING, new ButtonType[]{ButtonType.OK});
                return;
            }

            Herramienta herramientaOriginal = observableHerramientas
                    .stream()
                    .filter(h -> h.getId() == herramientaEnUsoSelecionada.getId())
                    .findFirst()
                    .orElse(null);
            if(herramientaOriginal!=null){
                herramientaOriginal.setStockDisponible(herramientaOriginal.getStockDisponible()+spnrDevolver.getValue());
                herramientaOriginal.setStockEnUso(herramientaOriginal.getStockEnUso()-spnrDevolver.getValue());

                HerramientaDAO db = new HerramientaDAOImp();
                boolean consulta1 = db.actualizarStockDisponible(herramientaEnUsoSelecionada.getId(),spnrDevolver.getValue(),Math.negateExact(spnrDevolver.getValue()));


                if(consulta1){
                    Alerts.showAlert("Éxito","La herramienta se ha marcado como devuelta.", Alert.AlertType.INFORMATION,new ButtonType[]{ButtonType.OK});
                }else{
                    Alerts.showAlert("Error","No se ha podido marcar la herramienta como devuelta. Intente de nuevo.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
                }

                if(herramientaEnUsoSelecionada.getStockEnUso()==spnrDevolver.getValue()){
                    observableHerramientasUso.remove(herramientaEnUsoSelecionada);
                }else{
                    herramientaEnUsoSelecionada.setStockEnUso(herramientaEnUsoSelecionada.getStockEnUso()-spnrDevolver.getValue());
                }

                RepararController.herramientasEnUso = new ArrayList<>(observableHerramientasUso);
                tblHerramientasUso.refresh();
                tblHerramientas.refresh();
            }else{
                Alerts.showAlert("Error","No se encontró la herramienta original.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            }
        }
    }

    public void btnRegresar_click(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Reparar.fxml"));
            loader.setControllerFactory(p->new RepararController(idReparacion));
            Parent vistaCentro = loader.load(); // Carga la vista y guarda el root

            // Obtener el controlador de esa vista
            Object controlador = loader.getController();

            // Si el controlador tiene un método para recibir el rootPane, lo llamas:
            if (controlador instanceof ControladorConRootPane) {
                ((ControladorConRootPane) controlador).setRootPane(rootPane);
            }
            if(controlador instanceof RepararController){
                ((RepararController) controlador).setIdReparacion(idReparacion);
            }

            rootPane.setCenter(vistaCentro);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
