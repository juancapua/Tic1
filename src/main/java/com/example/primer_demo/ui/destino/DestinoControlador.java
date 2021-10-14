package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Experiencia;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;

@Component
public class DestinoControlador {

    private ArrayList<Pane> experienciasContainer;

    @FXML
    private Text nombre_destino;

    @FXML
    private Text nombre_destino_scrolledDown;

    @FXML
    private Rectangle nombre_container;

    @FXML
    private Rectangle nombre_container_scrolledDown;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane base_bg;

    @FXML
    private Pane experiences;

    @FXML
    private Pane entries;

    public void scrolling(){
        if(scrollPane.getVvalue() == 305){
            nombre_destino.setVisible(false);

        }

    }

    public void init(Destino destino) {
        setNombre_destino(destino.getNombre());
        base_bg.setBackground(new Background(new BackgroundImage(new Image(destino.getImages().get(0)),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        for (Experiencia experience: destino.getExperiencias()) {
            agregarExperiencia();
        }
    }

    private void setNombre_destino(String nombre_destino){
        this.nombre_destino.setText(nombre_destino);
    }

    public void scrollPane(){

    }

    private void agregarExperiencia(){
        if(experienciasContainer.size() == 0) {
            Pane container = new Pane();
            container.layoutXProperty().set(0);
            container.layoutYProperty().set(0);
            container.setPrefHeight(200);
            container.setPrefWidth(200);
            container.setStyle("-fx-background-color: #000000");
            experiences.getChildren().add(container);
            experienciasContainer.add(container);
        }
    }

}
