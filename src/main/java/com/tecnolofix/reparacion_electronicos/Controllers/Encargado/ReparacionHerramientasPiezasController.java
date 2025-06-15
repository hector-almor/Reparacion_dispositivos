package com.tecnolofix.reparacion_electronicos.Controllers.Encargado;

import com.tecnolofix.reparacion_electronicos.Models.HerramientaConCantidad;
import com.tecnolofix.reparacion_electronicos.Models.PiezaConCantidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReparacionHerramientasPiezasController {
    @FXML Label lblCostoTotal;
    @FXML TableColumn<PiezaConCantidad,Integer> clmCantidadPieza;
    @FXML TableColumn<PiezaConCantidad,Double> clmCosto;
    @FXML TableColumn<PiezaConCantidad,String> clmDescripcionPieza;
    @FXML TableColumn<PiezaConCantidad,String> clmNombrePieza;
    @FXML TableColumn<PiezaConCantidad,Integer> clmIdPieza;
    @FXML Button btnRegresar;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmCantidadHerramienta;
    @FXML TableColumn<HerramientaConCantidad,String> clmDescripcionHerramienta;
    @FXML TableColumn<HerramientaConCantidad,String> clmNombreHerramienta;
    @FXML TableColumn<HerramientaConCantidad,Integer> clmIdHerramienta;
    @FXML TableView<HerramientaConCantidad> tblHerramientas;

    public void btnRegresar_click(ActionEvent actionEvent) {
    }
}
