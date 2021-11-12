package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Entrada;
import com.example.primer_demo.business.entities.Experiencia;
import eu.iamgio.froxty.FrostyBox;
import eu.iamgio.froxty.FrostyEffect;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.hibernate.usertype.LoggableUserType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DestinoControlador {

    private ArrayList<Pane> experienciasContainer;

    private Destino destino;
    private List<Image> images;
    private int currentPos = 0;

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
    private GridPane experiences;

    @FXML
    private GridPane entries;

    @FXML
    private Pane head;

    @FXML
    private Pane entriesPane;

    @FXML
    private Pane experiencesPane;

    @FXML
    private AnchorPane body;

    @FXML
    private Button nextBtn;

    @FXML
    private Button prevBtn;

    @FXML
    private Text departamento;

    public void scrolling(){
        System.out.println(scrollPane.getVvalue());
        if(scrollPane.getVvalue() == 0.3){
            nombre_destino.setVisible   (false);
            nombre_container.setVisible(false);

        }
    }

    public void init(Destino destino) throws IOException {
        //...

        this.destino = destino;
        setNombre_destino(destino.getNombre());
        InputStream x = new ByteArrayInputStream(destino.getImages().get(0));
        scrollPane.setBackground(new Background(new BackgroundImage(new Image(x),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //makeFrosty(nombre_container,base_bg);
        int currentY = 0;
        for (Entrada entrada: destino.getEntradas()) {
            agregarEntrada(entrada,currentY);
            currentY++;
        }
        currentY = 0;
        for (Experiencia experiencia: destino.getExperiencias()) {
            agregarExperiencia(experiencia,currentY);
            currentY++;
        }
        entries.setVgap(48);
        entries.setAlignment(Pos.CENTER);
        for (Node node: entries.getChildren()) {
            entries.setValignment(node, VPos.CENTER);
        }
        //makeFrosty(entriesPane,body);
        //makeFrosty(experiencesPane,body);

        experiences.setVgap(48);
        experiences.setHgap(24);
        for (Node node: experiences.getChildren()) {
            experiences.setValignment(node, VPos.CENTER);
        }


        images = new ArrayList<>();
        for (byte[] imagen: destino.getImages()) {
            InputStream y = new ByteArrayInputStream(imagen);
            images.add(new Image(y));
        }

        departamento.setText(destino.getDepartamento().getNombre_pk());

    }

    private void setNombre_destino(String nombre_destino){
        this.nombre_destino.setText(nombre_destino);
    }

    public void nextImg(){
        imagesRotation(1,currentPos);
    }

    public void prevImg(){
        imagesRotation(-1,currentPos);
    }

    public void scrollPane(){

    }

    private void agregarEntrada(Entrada entrada, int currentY) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane entrada_container = fxmlLoader.load(EntradaControlador.class.getResourceAsStream("entrada.fxml"));
        EntradaControlador entradaControlador = fxmlLoader.getController();
        entradaControlador.init(entrada);
        entrada_container.setId("entry");
        entries.add(entrada_container,0,currentY); // Adds the container to the scene
    }

    private void agregarExperiencia(Experiencia experiencia, int currentY) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane experienciaContainer = fxmlLoader.load(ExperienciaThumbnailControlador.class.getResourceAsStream("experienciaThumbnail.fxml"));
        ExperienciaThumbnailControlador experienciaThumbnailControlador = fxmlLoader.getController();
        experienciaThumbnailControlador.init(experiencia);
        experienciaContainer.setId("experience");
        experiences.add(experienciaContainer,0,currentY); // Adds the container to the scene
    }

    /*
    public void agregarExperiencia(Experiencia, int currentY){
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane experiencia_container = fxmlLoader.load(EntradaControlador.class.getResourceAsStream("experienciaThumbnail.fxml"));
        ExperienciaControlador experienciaControlador = fxmlLoader.getController();
        experienciaControlador.init(experiencia);
    }
    */

    private void makeFrosty(Node container, Pane targetPane){
        FrostyEffect effect = new FrostyEffect(); // Instantiates the effect. The parameters are optional and default to (0.5, 10)
        FrostyBox box = new FrostyBox(effect, container); // Instantiates a container with frosty effect
        box.setAntialiasingLevel(0.4); // See notes below
        box.setLayoutX(container.layoutXProperty().get());
        box.setLayoutY(container.layoutYProperty().getValue());
        targetPane.getChildren().add(box); // Adds the container to the scene
    }

    private void imagesRotation(int direction, int current){
        if(images.size()>1){
            if(current==0 && direction<0){
                current=images.size()-1;
                scrollPane.setBackground(new Background(new BackgroundImage(images.get(current), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
            } else if(current<images.size()-1 && direction>0) {
                current=0;
                scrollPane.setBackground(new Background(new BackgroundImage(images.get(current), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
            }
            scrollPane.setBackground(new Background(new BackgroundImage(images.get(current + direction), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        }
    }
}
