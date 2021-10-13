package com.example.primer_demo.ui.usuarioOperador;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.OperadorMgr;
import com.example.primer_demo.business.UsuarioOperadorMgr;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.entities.UsuarioOperador;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.admin.adminControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class UsuarioOperadorControlador implements Initializable {

    @Autowired
    private UsuarioOperadorMgr usuarioOperadorMgr;

    @Autowired
    private OperadorRepository operadorRepository;

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
    private ComboBox<String> operador;

    @FXML
    void close(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
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

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {

        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(Controlador.class.getResourceAsStream("samole.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();

        showAlert("Nos vemos pronto", "Se ha cerrado sesion correctamente");
    }


    @FXML
    void addUsuarioOperador(ActionEvent event) {
        if (txtUsuario.getText() == null || txtUsuario.getText().equals("") ||
                txtCorreo.getText() == null || txtCorreo.getText().equals("") ||
                txtContrasena.getText() == null || txtContrasena.getText().equals("") ||
                txtConfContrasena.getText() == null || txtConfContrasena.getText().equals("")

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
                Operador id_operador = operadorRepository.findByNombreDeUsuario(operador.getValue());


                try {

                    usuarioOperadorMgr.agregarUsuarioOperador(usuario, correo, contrasena, id_operador);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterable<Operador> operadores = operadorRepository.findAll();
        for(Operador x: operadores){
            operador.getItems().add(x.getNombreDeUsuario());
        }
    }
}
