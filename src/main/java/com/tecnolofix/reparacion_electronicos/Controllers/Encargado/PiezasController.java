package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.mysql.cj.xdevapi.DatabaseObject;
import com.tecnolofix.reparacion_electronicos.Models.Pieza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PiezasController {
    @FXML Button btnRegresar;
    @FXML TableColumn<Pieza, DatabaseObject> clmCosto;
    @FXML TableColumn<Pieza,Integer> clmStock;
    @FXML TableColumn<Pieza,String> clmDescripcion;
    @FXML TableColumn<Pieza,String> clmNNombre;
    @FXML TableColumn<Pieza,Integer> clmId;
    @FXML TableView<Pieza> tblPiezas;

    public void btnRegresar_click(ActionEvent actionEvent) {
    }
}
