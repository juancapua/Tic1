package com.example.primer_demo.ui;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.AdminMgr;
import com.example.primer_demo.business.OperadorMgr;
import com.example.primer_demo.business.UsuarioMgr;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.AdminRepository;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import com.example.primer_demo.ui.admin.adminControlador;
import com.example.primer_demo.ui.operador.operadorControlador;
import com.example.primer_demo.ui.usuario.UsuarioControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controlador {

    private Parent root;

    @Autowired
    private OperadorMgr operadorMgr;

    @Autowired
    private UsuarioMgr usuarioMgr;

    @Autowired
    private AdminMgr adminMgr;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private OperadorRepository operadorRepository;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtContrasena;

    @FXML
    void agregarUsuario(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(UsuarioControlador.class.getResourceAsStream("sample2.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();


    }
    @FXML
    void login(ActionEvent event) throws Exception{
        if (txtUsuario.getText() == null || txtUsuario.getText().equals("") ||
                txtContrasena.getText() == null || txtContrasena.getText().equals("")) {

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        } else {

            try {

                String usuario = txtUsuario.getText();
                String contrasena = txtContrasena.getText();


                try {

                    if (adminMgr.ingresar(usuario, contrasena)){
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

                    else if(operadorMgr.ingresar(usuario, contrasena) && operadorRepository.findByNombreDeUsuarioAndContrasena(usuario, contrasena).getEstado()){
                        close(event);
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

                        root = fxmlLoader.load(operadorControlador.class.getResourceAsStream("operador.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image("images/logo_final.png"));
                        stage.setResizable(false);
                        stage.show();


                    }

                    else if (usuarioMgr.ingresar(usuario, contrasena)){
                        close(event);
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

                        root = fxmlLoader.load(InicioControlador.class.getResourceAsStream("inicio.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image("images/logo_final.png"));
                        stage.setResizable(false);
                        InicioControlador controlador = fxmlLoader.getController();
                        controlador.setLabel(usuarioRepository.findById(usuario).get().getNombreDeUsuario());
                        stage.show();

                    }else{
                        showAlert("Contraseña incorrecta", "La contraseña ingresada no es la correcta");
                    }
                } catch (InvalidInformation invalidInformation) {

                    showAlert(
                            "Informacion invalida !",
                            "Se encontro un error en los datos ingresados.");
                } catch (UsuarioNotExist usuarioNotExist) {
                    showAlert(
                            "usuario no registrado !",
                            "El nombre indicado no ha sido registrado en el sistema.");
                }

            } catch (NumberFormatException e) {

                showAlert(
                        "Datos incorrectos !",
                        "El usuario no tiene el formato esperado (numerico).");

            }
        }
    }

    private void close(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//
//    public void cambiarASample(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//
//    }
//    public void cambiarASample2(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//    }

    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

}
