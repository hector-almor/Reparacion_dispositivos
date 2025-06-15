package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HerramientasController {
    @FXML Button btnRegresar;
    @FXML TableColumn clmStockUso;
    @FXML TableColumn clmStockDisponible;
    @FXML TableColumn clmDescripcion;
    @FXML TableColumn clmNombre;
    @FXML TableColumn clmId;
    @FXML TableView tblHerramientas;

    public void btnRegresar_click(ActionEvent actionEvent) {
    }
}
