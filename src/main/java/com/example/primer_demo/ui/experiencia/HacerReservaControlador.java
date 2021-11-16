package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.business.entities.Experiencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTimePicker;
import eu.iamgio.animated.AnimatedHBox;
import eu.iamgio.animated.AnimationPair;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.time.LocalDate;

@Component
public class HacerReservaControlador {

    @FXML
    private HBox tituloExperiencia;

    @FXML
    private AnchorPane titulo;

    @FXML
    private AnchorPane imagen;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private MFXDatePicker fechaInicio;
    @FXML
    private MFXDatePicker fechaFin;
    @FXML
    private Text textoInicio;
    @FXML
    private Text textoFin;

    @FXML
    private MFXButton siguiente;

    @FXML
    private Tab tab1;
    @FXML
    private Tab tab2;
    @FXML
    private Tab tab3;
    @FXML
    private Tab tab4;

    private AnimatedHBox animatedHBox;

    private Text textoDepartamento;
    private Text textoDestino;
    private Text textoExperiencia;

    public void init(Experiencia experiencia) throws InterruptedException {
        animatedHBox = new AnimatedHBox(AnimationPair.fade());
        titulo.getChildren().add(animatedHBox);
        animatedHBox.setVisible(true);
        animatedHBox.setPadding(new Insets(12));
        textoDepartamento = newText(experiencia.getDestino().getDepartamento().getNombre_pk() + ", " + experiencia.getDestino().getNombre() + ", " + experiencia.getNombre(),24);
        animatedHBox.getChildren().add(textoDepartamento);
        try {
            imagen.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream(experiencia.getImagen())), null, null, BackgroundPosition.CENTER, null)));
        }catch (Exception e){
        }
        tab2.setDisable(true);
        tab3.setDisable(true);
        tab4.setDisable(true);

        if (experiencia.getTipo()!=null){
            if(experiencia.getTipo().equals("PED")) {
                fechaFin.setVisible(false);
                textoFin.setVisible(false);
            }
        }

    }

    private Text newText(String texto, int size){
        Text text = new Text();
        text.setText(texto);
        text.setVisible(true);
        text.fontProperty().set(Font.font("Segoe UI" ,size));
        return text;
    }

    public void dateCheck(){
        if(fechaInicio.getDate() != null && fechaInicio.getDate().isAfter(LocalDate.now())){
            if(fechaFin.getDate() != null && fechaFin.getDate().isAfter(fechaInicio.getDate())){
                siguiente.setDisable(false);
                tab2.setDisable(false);
            } else if(fechaFin.getDate()==null){
                siguiente.setDisable(false);
                tab2.setDisable(false);
            }else {
                siguiente.setDisable(true);
                tab2.setDisable(true);
                tab3.setDisable(true);
                tab4.setDisable(true);
            }
        } else {
            siguiente.setDisable(true);
            tab2.setDisable(true);
            tab3.setDisable(true);
            tab4.setDisable(true);
        }
    }

    public void siguiente(){
        tabPane.getSelectionModel().select(1);
    }
}
