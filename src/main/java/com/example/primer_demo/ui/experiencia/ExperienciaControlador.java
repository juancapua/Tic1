package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.business.entities.Experiencia;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class ExperienciaControlador {

    @FXML
    private Text experiencia_nombre;

    @FXML
    private Text destino_nombre;

    @FXML
    private Text fav_icon;

    private boolean faved = false;

    public void init(Experiencia experiencia) {
        experiencia_nombre.setText(experiencia.getNombre());
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
}
