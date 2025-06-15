package com.tecnolofix.reparacion_electronicos.Models;

import javafx.scene.control.*;

public class Alerts {
    public static Boolean[] showAlert(String titulo, String mensaje, Alert.AlertType alertType, ButtonType[] buttons) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.getButtonTypes().setAll(buttons);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        final Boolean[] a = {true};
        alert.showAndWait().ifPresentOrElse(response->{
            if(!(response==ButtonType.OK)) {
                a[0] = false;
            }
        },()->a[0]=false);
        return a;
    }
}