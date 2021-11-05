package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.Experiencia;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExperienciaControlador {

    private Experiencia experiencia;

    @FXML
    private Text experiencia_nombre;

    @FXML
    private Text destino_nombre;

    @FXML
    private Text fav_icon;

    private boolean faved = false;

    public void init(Experiencia experiencia) {
        this.experiencia = experiencia;
        experiencia_nombre.setText(experiencia.getNombre());
        destino_nombre.setText(experiencia.getDestino().getNombre());
    }

    public void clicked(){
        if(!faved) {
            fav_icon.setText("\uE735");
            faved = true;
        } else {
            fav_icon.setText("\uE734");
            faved = false;
        }
    }

    public void mouseEnter(){
        if(!faved) {
            fav_icon.setText("\uE735");
        }
    }

    public void mouseExit(){
        if(!faved) {
            fav_icon.setText("\uE734");
        }
    }

    public void reservar(ActionEvent actionEvent) throws IOException, InterruptedException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(ExperienciaControlador.class.getResourceAsStream("reserva.fxml"));
        ReservaControlador reservaControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        reservaControlador.init(experiencia);
    }
}

