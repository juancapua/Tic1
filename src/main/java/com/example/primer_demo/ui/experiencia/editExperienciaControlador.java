package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.business.ExperienciaMgr;
import com.example.primer_demo.business.entities.Dia;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.persistance.PaisRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class editExperienciaControlador {

    private Parent root;

    private Experiencia experiencia;

    @Autowired
    private ExperienciaMgr experienciaMgr;

    private Boolean reserva;

    public void setExperiencia(Experiencia experiencia){
        this.experiencia = experiencia;
        if(this.experiencia.getSe_reserva()){
                CheckBox interestCheckBox = new CheckBox("No se reserva mas?");
                interestCheckBox.setUserData(false);
                reservabox.getChildren().add(interestCheckBox);
                this.reserva = false;
        }
        else {
            CheckBox interestCheckBox = new CheckBox("Ahora se reserva?");
            reservabox.getChildren().add(interestCheckBox);
            this.reserva = true;
        }
    }

    @FXML
    private TextField aforotxt;

    @FXML
    private TextField desctxt;

    @FXML
    private TextField horaAperturatxt;

    @FXML
    private TextField horaCierretxt;

    @FXML
    private VBox reservabox;

    @FXML
    private TextField duraciontxt;

    @FXML
    void editExperiencia(ActionEvent event){
        Boolean entro = false;
        if(desctxt.getText() != null && !desctxt.getText().equals("")){
            experienciaMgr.cambiarDescripcion(this.experiencia, desctxt.getText());
            entro = true;
        }
        if(horaAperturatxt.getText() != null && !horaAperturatxt.getText().equals("")){
            experienciaMgr.cambiarApertura(this.experiencia, LocalTime.parse(horaAperturatxt.getText()));
            entro = true;
        }
        if(horaCierretxt.getText() != null && !horaCierretxt.getText().equals("")){
            experienciaMgr.cambiarCierre(this.experiencia, LocalTime.parse(horaCierretxt.getText()));
            entro = true;
        }
        if(aforotxt.getText() != null && !aforotxt.getText().equals("")){
            experienciaMgr.cambiarAforo(this.experiencia, Integer.parseInt(aforotxt.getText()));
            entro = true;
        }
        if(duraciontxt.getText() != null && !duraciontxt.getText().equals("")){
            experienciaMgr.cambiarDuracion(this.experiencia, Integer.parseInt(duraciontxt.getText()));
            entro = true;
        }
        for(Node node: reservabox.getChildren()){
            CheckBox checkBox = (CheckBox) node;
            if(checkBox.isSelected()){
                experienciaMgr.cambiarReserva(this.experiencia, this.reserva);
                entro = true;
            }
        }
        if(entro){
            showAlert("Cambio de datos", "Los datos se guardaron exitosamente");
            close(event);
        }
        else {
            showAlert("Sin cambios", "No se registraron nuevos cambios o los mismos son incorrectos");
        }



    }

    @FXML
    void volver(javafx.event.ActionEvent event) {
        close(event);
    }


    private void close(javafx.event.ActionEvent event) {
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
