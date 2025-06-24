package com.tecnolofix.reparacion_electronicos.Controllers;

import com.tecnolofix.reparacion_electronicos.Controllers.Tecnico.PrincipalTecnicoController;
import com.tecnolofix.reparacion_electronicos.DB.DAO.TecnicoDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.TecnicoDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.PdfGarantia;
import com.tecnolofix.reparacion_electronicos.Models.PdfOrdenReparacion;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.ResourceBundle;

public class InicioController implements Initializable {

    @FXML public TextField txtUsuario;
    @FXML public Button btnIngresar;
    @FXML ToggleButton toggleButtonEncargado;
    @FXML ToggleButton toggleButtonTecnico;
    @FXML Button btnPdfOrden;
    @FXML Button btnPdfGarantia;
    @FXML ToggleGroup groupRol;
    @FXML PasswordField txtPassword;

    public void btnIngresar_click(ActionEvent actionEvent) throws IOException {
        if(toggleButtonTecnico.isSelected()) {
            TecnicoDAO dbTecnico = new TecnicoDAOImp();
            Tecnico tecnico = new Tecnico();
            tecnico.setUsuario(txtUsuario.getText());
            tecnico.setContraseña(txtPassword.getText());
            tecnico =  dbTecnico.loginTecnico(tecnico);
            if(tecnico!=null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Principal_tecnico.fxml"));
                Tecnico finalTecnico = tecnico;
                loader.setControllerFactory(p->new PrincipalTecnicoController(finalTecnico));
                Parent root = loader.load();

                Scene scene = new Scene(root); // Crea la escena antes

                Stage newStage = new Stage();
                newStage.setTitle("Dashboard");
                newStage.setScene(scene);       // Asigna la escena
                newStage.sizeToScene();         // Ajusta el tamaño DESPUÉS de setScene
                newStage.setMinWidth(1131);
                newStage.setMinHeight(828);
                newStage.show();                // Luego muestra la ventana



                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();
            }
            else{
                Alerts.showAlert("Error","Usuario y/o contraseña incorrectos.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            }
        }else{
            try(Connection conn = DriverManager.getConnection(DB.getUrl(),txtUsuario.getText().trim(),txtPassword.getText().trim())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Encargado/Principal_encargado.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root); // Crea la escena antes

                Stage newStage = new Stage();
                newStage.setTitle("Dashboard");
                newStage.setScene(scene);       // Asigna la escena
                newStage.sizeToScene();         // Ajusta el tamaño DESPUÉS de setScene
                newStage.setMinWidth(1131);
                newStage.setMinHeight(828);
                newStage.show();                // Luego muestra la ventana



                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

            }catch (Exception ex){
                ex.printStackTrace();
                Alerts.showAlert("Error","Usuario y/o contraseña incorrectos.", Alert.AlertType.ERROR,new ButtonType[]{ButtonType.OK});
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleButtonEncargado.setOnAction(event -> actualizarEstilos());
        toggleButtonTecnico.setOnAction(event -> actualizarEstilos());

        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new IOException("No se pudo encontrar el archivo app.properties en resources.");
            }
            prop.load(input);
            DB.setUrl(prop.getProperty("db.url"));
            DB.setUsername(prop.getProperty("db.user"));
            DB.setPassword(prop.getProperty("db.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualizarEstilos() {
        if (toggleButtonEncargado.isSelected()) {
            toggleButtonEncargado.setStyle("-fx-background-color: #117a8b; -fx-border-color: #0f6674; -fx-text-fill: white;");
            toggleButtonTecnico.setStyle(""); // Restaurar estilo original (vuelve a usar .button-info)
        } else if (toggleButtonTecnico.isSelected()) {
            toggleButtonTecnico.setStyle("-fx-background-color: #117a8b; -fx-border-color: #0f6674; -fx-text-fill: white;");
            toggleButtonEncargado.setStyle("");
        }
    }

    public void btnPdfGarantia_click(ActionEvent actionEvent) {
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Generar Garantía");
//        dialog.setHeaderText("Ingrese el ID de la orden entregada");
//        dialog.setContentText("ID de la orden:");
//
//        dialog.showAndWait().ifPresent(idStr -> {
//            try {
//                int idOrden = Integer.parseInt(idStr);
//                PdfGarantia.generarGarantia(idOrden);
//                Alerts.showAlert("Éxito", "PDF generado correctamente en tu Escritorio.", Alert.AlertType.INFORMATION, new ButtonType[]{ButtonType.OK});
//            } catch (NumberFormatException e) {
//                Alerts.showAlert("Error", "El ID debe ser un número", Alert.AlertType.ERROR, new ButtonType[]{ButtonType.OK});
//            }
//        });

        int idOrden = 25; // ID fijo como pediste

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    PdfGarantia.generarGarantia(idOrden);
                    Platform.runLater(() -> Alerts.showAlert(
                            "Éxito",
                            "PDF generado correctamente en el directorio actual.",
                            Alert.AlertType.INFORMATION,
                            new ButtonType[]{ButtonType.OK}
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> Alerts.showAlert(
                            "Error",
                            "Ocurrió un error al generar el PDF:\n" + e.getMessage(),
                            Alert.AlertType.ERROR,
                            new ButtonType[]{ButtonType.OK}
                    ));
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    public void btnPdfOrden_click(ActionEvent actionEvent) {

//        int idOrden = 24; // ID fijo como pediste
//        String destino = "orden_" + idOrden + ".pdf";
//
//        PdfOrdenReparacion generador = new PdfOrdenReparacion();
//        try {
//            generador.generarPDF(idOrden, destino);
//            System.out.println("PDF generado correctamente en: " + new File(destino).getAbsolutePath());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


            int idOrden = 24;
            String destino = "orden_" + idOrden + ".pdf";

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    PdfOrdenReparacion generador = new PdfOrdenReparacion();
                    try {
                        generador.generarPDF(idOrden, destino);
                        System.out.println("PDF generado correctamente en: " + new File(destino).getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };

            new Thread(task).start();
    }
}