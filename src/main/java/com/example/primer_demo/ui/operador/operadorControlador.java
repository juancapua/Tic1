package com.example.primer_demo.ui.operador;


import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.OperadorMgr;
import com.example.primer_demo.business.entities.Pais;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.admin.adminControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class operadorControlador {

    @Autowired
    private OperadorMgr operadorMgr;

    private Parent root;


    @FXML
    private Button btnVolver;

    @FXML
    private Button btnAgregar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtContrasena;

    @FXML
    private TextField txtConfContrasena;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    void close(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


    @FXML
    void addOperador(ActionEvent event) {
        if (txtUsuario.getText() == null || txtUsuario.getText().equals("") ||
                txtCorreo.getText() == null || txtCorreo.getText().equals("") ||
                txtContrasena.getText() == null || txtContrasena.getText().equals("") ||
                txtConfContrasena.getText() == null || txtConfContrasena.getText().equals("") ||
                txtTelefono.getText() == null || txtTelefono.getText().equals("")||
                txtDireccion.getText() == null || txtDireccion.getText().equals("")

        ) {

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        }if(!txtContrasena.getText().equals(txtConfContrasena.getText())){
            showAlert("Datos incorrectos", "Las contraseñas no coinciden");

        } else {

            try {

                String usuario = txtUsuario.getText();
                String correo = txtCorreo.getText();
                String contrasena = txtContrasena.getText();
                int telefono = Integer.parseInt(txtTelefono.getText());
                String direccion = txtDireccion.getText();


                try {

                    operadorMgr.agregarOperador(usuario, correo, contrasena, telefono, direccion);

                    showAlert("Cliente agregado", "Se agrego con exito el operador!");

                    close(event);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

                    root = fxmlLoader.load(adminControlador.class.getResourceAsStream("admin.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.getIcons().add(new Image("images/logo_final.png"));
                    stage.setResizable(false);
                    stage.show();
                } catch (InvalidInformation invalidInformation) {

                    showAlert(
                            "Informacion invalida !",
                            "Se encontro un error en los datos ingresados.");
                }catch (UsuarioAlreadyExist usuarioAlreadyExist){
                    showAlert(
                            "Documento ya registrado !",
                            "El nombre indicado ya ha sido registrado en el sistema.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {

                showAlert(
                        "Datos incorrectos !",
                        "El documento no tiene el formato esperado (numerico).");

            }
        }

    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {

        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(Controlador.class.getResourceAsStream("sample.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();

        showAlert("Nos vemos pronto", "Se ha cerrado sesion correctamente");
    }
    
    @FXML
    void volverAtras(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(adminControlador.class.getResourceAsStream("admin.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();
        
    }

    private void clean() {
        txtUsuario.setText(null);
        txtCorreo.setText(null);
        txtConfContrasena.setText(null);
        txtContrasena.setText(null);
    }

    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
}
