package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Models.HerramientaConCantidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RevisionHerramientasController {
    @FXML Button btnRegresar;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmCantidad;
    @FXML TableColumn<HerramientaConCantidad,String> clmDescripcion;
    @FXML TableColumn<HerramientaConCantidad,String> clmNombre;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmId;
    @FXML TableView<HerramientaConCantidad> tblHerramientas;

    public void btnRegresar_click(ActionEvent actionEvent) {
    }
}
