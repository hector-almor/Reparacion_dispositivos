package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TecnicosController {
    @FXML TableColumn<Tecnico,String> clmEspecialidad;
    @FXML TableColumn<Tecnico,String> clmCorreo;
    @FXML TableColumn<Tecnico,String> clmTelefono;
    @FXML TableColumn<Tecnico,String> clmNombre;
    @FXML TableColumn<Tecnico,Integer> clmId;
    @FXML TableView<Tecnico> tblTecnicos;
}
