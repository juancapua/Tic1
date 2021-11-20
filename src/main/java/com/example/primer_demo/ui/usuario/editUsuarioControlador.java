package com.example.primer_demo.ui.usuario;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.EtiquetaMgr;
import com.example.primer_demo.business.UsuarioMgr;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class editUsuarioControlador {

    @Autowired
    private UsuarioMgr usuarioMgr;

    @Autowired
    private EtiquetaMgr etiquetaMgr;

    private Usuario usuario;

    private Parent root;

    @FXML
    private PasswordField confNuevaConttxt;

    @FXML
    private VBox interesesBox;

    @FXML
    private PasswordField nuevaContratxt;

    @FXML
    private VBox vacunaBox;

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
        if(!this.usuario.getVacunado()){
            CheckBox vacuna = new CheckBox("Esta vacunado/a");
            vacunaBox.getChildren().add(vacuna);
        }
        for(Etiqueta x: etiquetaMgr.listaEtiquetas()){
            CheckBox interestCheckBox = new CheckBox(x.getNombre());
            interestCheckBox.setUserData(x);
            interesesBox.getChildren().add(interestCheckBox);
        }
    }

    @FXML
    void cambiarDatos(ActionEvent event) throws IOException {
        Boolean entro = false;
        if(nuevaContratxt.getText() != null && !nuevaContratxt.getText().equals("") && confNuevaConttxt.getText() != null && !confNuevaConttxt.getText().equals("") && nuevaContratxt.getText().equals(confNuevaConttxt.getText())){
            usuarioMgr.cambiarContrasena(this.usuario, nuevaContratxt.getText());
            entro = true;
        }
        if(vacunaBox.getChildren().size() > 0){
            for(Node node:vacunaBox.getChildren()){
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()){
                    usuarioMgr.habilitarVacuna(this.usuario);
                }
            }
            entro = true;
        }
        Set<Etiqueta> etiquetas = new HashSet<>();

        for(Node node:interesesBox.getChildren()){
            CheckBox checkBox = (CheckBox) node;
            if(checkBox.isSelected()){
                etiquetas.add((Etiqueta) checkBox.getUserData());
            }
        }
        if(etiquetas.size()>0){
            usuarioMgr.cambiarIntereses(this.usuario, etiquetas);
            entro = true;
        }
        if(entro){
            showAlert("Cambio de datos", "Los datos se guardaron exitosamente");
            close(event);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

            root = fxmlLoader.load(vistaPerfilControlador.class.getResourceAsStream("vistaPerfil.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("images/logo_final.png"));
            stage.setResizable(false);
            vistaPerfilControlador vistaPerfilControlador =fxmlLoader.getController();
            vistaPerfilControlador.setUsuario(this.usuario);
            stage.show();
        }
        else {
            showAlert("Sin cambios", "No se registraron nuevos cambios o los mismos son incorrectos");

        }

    }

    @FXML
    void volver(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(vistaPerfilControlador.class.getResourceAsStream("vistaPerfil.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        vistaPerfilControlador vistaPerfilControlador =fxmlLoader.getController();
        vistaPerfilControlador.setUsuario(this.usuario);
        stage.show();
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
