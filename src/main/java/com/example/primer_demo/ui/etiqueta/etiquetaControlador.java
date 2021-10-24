package com.example.primer_demo.ui.etiqueta;

import com.example.primer_demo.business.EtiquetaMgr;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Component;
import javafx.scene.control.TextField;

import java.awt.*;

@Component
public class etiquetaControlador {

    @Autowired
    private EtiquetaMgr etiquetaMgr;

    @FXML
    private Button button;

    @FXML
    private TextField nuevaEtiqueta;


    @FXML
    void addEtiqueta(ActionEvent event){

        if(nuevaEtiqueta.getText().equals("")||nuevaEtiqueta.getText() == null){
            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");
        }

        String nombre = nuevaEtiqueta.getText();

        try {
            etiquetaMgr.agregarEtiqueta(nombre);

            showAlert("Interes agregado", "Se agrego con exito el interes!");

            close(event);
        } catch (UsuarioAlreadyExist usuarioAlreadyExist) {
            usuarioAlreadyExist.printStackTrace();
        }

    }


    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    @FXML
    void close(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
