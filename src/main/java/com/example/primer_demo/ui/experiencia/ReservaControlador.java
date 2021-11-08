package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.business.entities.Experiencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import eu.iamgio.animated.AnimatedHBox;
import eu.iamgio.animated.AnimationPair;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import javax.swing.text.html.ImageView;

@Component
public class ReservaControlador {

    @FXML
    private HBox tituloExperiencia;

    @FXML
    private AnchorPane titulo;

    @FXML
    private AnchorPane imagen;

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
        imagen.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream(experiencia.getImagen())), null, null, BackgroundPosition.CENTER, null)));

    }

    private Text newText(String texto, int size){
        Text text = new Text();
        text.setText(texto);
        text.setVisible(true);
        text.fontProperty().set(Font.font("Segoe UI" ,size));
        return text;
    }
}
