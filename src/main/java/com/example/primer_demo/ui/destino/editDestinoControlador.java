package com.example.primer_demo.ui.destino;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.EtiquetaMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.ui.usuario.vistaPerfilControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class editDestinoControlador {

    private Destino destino;

    @Autowired
    private DestinoMgr destinoMgr;

    @Autowired
    private EtiquetaMgr etiquetaMgr;

    @FXML
    private VBox BoxIntereses;

    @FXML
    private TextField aforotxt;

    @FXML
    private TextField contactotxt;

    @FXML
    private TextField descripciontxt;

    @FXML
    private TextField hor_aper_txt;

    @FXML
    private TextField hor_cie_txt;

    public void setDestino(Destino destino){
        this.destino = destino;
        for(Etiqueta x: etiquetaMgr.listaEtiquetas()){
            CheckBox interestCheckBox = new CheckBox(x.getNombre());
            interestCheckBox.setUserData(x);
            BoxIntereses.getChildren().add(interestCheckBox);
        }
    }

    @FXML
    public void cambiarDatos(ActionEvent event){
        Boolean entro = false;
        if(contactotxt.getText() != null && !contactotxt.getText().equals("")){
            destinoMgr.cambiarContacto(this.destino, contactotxt.getText());
            entro = true;
        }
        if(aforotxt.getText() != null && !aforotxt.getText().equals("")){
            destinoMgr.cambiarAforo(this.destino, Integer.parseInt(aforotxt.getText()));
            entro = true;
        }
        if(hor_aper_txt.getText() != null && !hor_aper_txt.getText().equals("")){
            destinoMgr.cambiarApertura(this.destino, LocalTime.parse(hor_aper_txt.getText()));
            entro = true;
        }
        if(hor_cie_txt.getText() != null && !hor_cie_txt.getText().equals("")){
            destinoMgr.cambiarCierre(this.destino, LocalTime.parse(hor_cie_txt.getText()));
            entro = true;
        }
        if(descripciontxt.getText() != null && !descripciontxt.getText().equals("")){
            destinoMgr.cambiarDescripcion(this.destino, descripciontxt.getText());
            entro = true;
        }
        Set<Etiqueta> etiquetas = new HashSet<>();

        for(Node node:BoxIntereses.getChildren()){
            CheckBox checkBox = (CheckBox) node;
            if(checkBox.isSelected()){
                etiquetas.add((Etiqueta) checkBox.getUserData());
            }
        }
        if(etiquetas.size()>0){
            destinoMgr.cambiarEtiquetas(this.destino, etiquetas);
            entro = true;
        }
        if(entro){
            showAlert("Cambio de datos", "Los datos se guardaron exitosamente");
            close(event);
        }
        else{
            showAlert("Sin cambios", "No se registraron nuevos cambios o los mismos son incorrectos");
        }
    }

    @FXML
    void volver(ActionEvent event) {
        close(event);
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
