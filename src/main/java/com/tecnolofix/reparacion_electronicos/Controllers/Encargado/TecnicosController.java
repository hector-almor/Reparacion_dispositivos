package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.DB.DAO.TecnicoDAO;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.TecnicoDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TecnicosController implements Initializable {
    @FXML TableColumn<Tecnico,String> clmEspecialidad;
    @FXML TableColumn<Tecnico,String> clmCorreo;
    @FXML TableColumn<Tecnico,String> clmTelefono;
    @FXML TableColumn<Tecnico,String> clmNombre;
    @FXML TableColumn<Tecnico,Integer> clmId;
    @FXML TableView<Tecnico> tblTecnicos;

    ObservableList<Tecnico> observableTecnicos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        clmEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));

        TecnicoDAO db = new TecnicoDAOImp();
        ArrayList<Tecnico> listaTecnicos = db.getAllTecnicos();
        observableTecnicos.setAll(listaTecnicos);
        tblTecnicos.setItems(observableTecnicos);
    }
}
