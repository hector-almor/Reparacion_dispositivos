package com.tecnolofix.reparacion_electronicos.Controllers;

import com.tecnolofix.reparacion_electronicos.Controllers.Tecnico.PrincipalTecnicoController;
import com.tecnolofix.reparacion_electronicos.DB.DAO.TecnicoDAO;
import com.tecnolofix.reparacion_electronicos.DB.DB;
import com.tecnolofix.reparacion_electronicos.DB.Implementaciones.TecnicoDAOImp;
import com.tecnolofix.reparacion_electronicos.Models.Alerts;
import com.tecnolofix.reparacion_electronicos.Models.Tecnico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    public ToggleButton toggleButtonEncargado;
    public ToggleButton toggleButtonTecnico;
    @FXML ToggleGroup groupRol;
    @FXML PasswordField txtPassword;

    public void btnIngresar_click(ActionEvent actionEvent) throws IOException {
        if(toggleButtonTecnico.isSelected()) {
            TecnicoDAO dbTecnico = new TecnicoDAOImp();
            Tecnico tecnico = new Tecnico();
            tecnico.setUsuario(txtUsuario.getText());
            tecnico.setContraseña(txtPassword.getText());
//            tecnico =  dbTecnico.loginTecnico(tecnico);
//            if(tecnico != null) {
            if(true){
                PrincipalTecnicoController.sesionTecnico = tecnico;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tecnolofix/reparacion_electronicos/Tecnico/Principal_tecnico.fxml"));
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
            try(Connection conn = DriverManager.getConnection(DB.getUrl(),"admin","Administrador123")){
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
}
