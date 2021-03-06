package com.example.primer_demo.ui;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.AdminMgr;
import com.example.primer_demo.business.OperadorMgr;
import com.example.primer_demo.business.UsuarioMgr;
import com.example.primer_demo.business.UsuarioOperadorMgr;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.AdminRepository;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioOperadorRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import com.example.primer_demo.ui.admin.adminControlador;
import com.example.primer_demo.ui.operador.operadorControlador;
import com.example.primer_demo.ui.usuario.UsuarioControlador;
import com.example.primer_demo.ui.usuarioOperador.HomeUsuarioOperador;
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
    private UsuarioOperadorMgr usuarioOperadorMgr;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UsuarioOperadorRepository usuarioOperadorRepository;

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

    public static Usuario usuario;

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
                        operadorControlador operadorControlador = fxmlLoader.getController();
                        operadorControlador.setOperador(usuario);
                        stage.show();


                    }

                    else if(usuarioOperadorMgr.ingresar(usuario, contrasena) && usuarioOperadorRepository.findByNombreDeUsuarioAndContrasena(usuario, contrasena).getEstado()){
                        close(event);
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

                        root = fxmlLoader.load(HomeUsuarioOperador.class.getResourceAsStream("homeUsuarioOperador.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image("images/logo_final.png"));
                        stage.setResizable(false);
                        HomeUsuarioOperador homeUsuarioOperador = fxmlLoader.getController();
                        homeUsuarioOperador.setOperador(usuario);
                        stage.show();
                    }

                    else if(usuarioMgr.ingresar(usuario, contrasena)){
                        close(event);
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

                        root = fxmlLoader.load(InicioControlador.class.getResourceAsStream("inicio.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image("images/logo_final.png"));
                        stage.setResizable(false);
                        this.usuario = usuarioMgr.traerUsuario(usuario);
                        InicioControlador controlador = fxmlLoader.getController();
                        controlador.setUsuario(this.usuario);
                        stage.show();

                    }else{
                        showAlert("Contrase??a incorrecta", "La contrase??a ingresada no es la correcta");
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


    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

}
