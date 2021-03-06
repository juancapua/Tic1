package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Entrada;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Usuario;
import eu.iamgio.froxty.FrostyBox;
import eu.iamgio.froxty.FrostyEffect;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
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
public class VerDestinoControlador {

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
    private VBox entries;

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

    private Usuario usuario;

    @FXML
    private Text duracionLabel;

    public void scrolling(){
        System.out.println(scrollPane.getVvalue());
        if(scrollPane.getVvalue() == 0.3){
            nombre_destino.setVisible   (false);
            nombre_container.setVisible(false);

        }
    }

    private Stage primaryStage;

    public void init(Destino destino, Stage stage) throws IOException {
        //...
        primaryStage = stage;
        this.destino = destino;
        setNombre_destino(destino.getNombre());
        try {
            InputStream x = new ByteArrayInputStream(destino.getImages().get(0));
            head.setBackground(new Background(new BackgroundImage(new Image(x), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        } catch (Exception e){

        }
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Platform.runLater(() -> setFasterScroller(scrollPane));


        body.setFocusTraversable(false);
        body.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(body.getViewOrder());
                body.setStyle("-fx-background-color: transparent");
            }
        });

        base_bg.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(body.focusedProperty());
            }
        });



//        scrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println(">> Mouse Clicked");
//                event.consume();
//            }
//        });

        entries.setSpacing(24);
        entries.setAlignment(Pos.CENTER);
        entries.setPrefWidth(entriesPane.getPrefWidth());
        for (Node node: entries.getChildren()) {
            //entries.setValignment(node, VPos.CENTER);
        }


        //makeFrosty(nombre_container,base_bg);
        int currentY = 0;
        for (Entrada entrada: destino.getEntradas()) {
            agregarEntrada(entrada,currentY);
            currentY++;
        }
        currentY = 0;
        for (Experiencia experiencia: destino.getExperiencias()) {
            if(experiencia.getEsta_autorizada()==true) {
                agregarExperiencia(experiencia, currentY);
                currentY++;
            }
        }


        if (entriesPane.getPrefHeight() > experiencesPane.getPrefHeight()) {
            experiencesPane.setPrefHeight(entriesPane.getPrefHeight());
        }

        //makeFrosty(entriesPane,body);
        //makeFrosty(experiencesPane,body);

        //experiences.setVgap(48);
        experiences.setHgap(2);
        for (Node node: experiences.getChildren()) {
            //experiences.setValignment(node, VPos.CENTER);
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
        //AnchorPane entrada_container = fxmlLoader.load(EntradaControlador.class.getResourceAsStream("entrada.fxml"));
        //EntradaControlador entradaControlador = fxmlLoader.getController();
        //entradaControlador.init(entrada);
        //entrada_container.setId("entry");
        //entries.addAll(entrada_container,0,currentY); // Adds the container to the scene
        Text t = new Text(entrada.getTitulo());
        t.setFont(new Font(38));
        t.setWrappingWidth(entries.getPrefWidth()-200);
        t.setTextAlignment(TextAlignment.CENTER);
        Double extraHeight = t.getBoundsInLocal().getHeight();
        entries.getChildren().add(t);
        t = new Text(entrada.getTexto());
        t.setWrappingWidth(entries.getPrefWidth()-200);
        t.setTextAlignment(TextAlignment.CENTER);
        t.setFont(new Font(22));
        extraHeight += t.getBoundsInLocal().getHeight();
        entriesPane.setPrefHeight(entriesPane.getHeight() + extraHeight);
        entries.getChildren().add(t);
        Line line = new Line();
        line.setStartX(0.0f);
        line.setStartY(0.0f);
        line.setEndX(entries.getPrefWidth()-200);
        line.setEndY(0.0f);
        entries.getChildren().add(line);

    }

    private void agregarExperiencia(Experiencia experiencia, int currentY) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane experienciaContainer = fxmlLoader.load(ExperienciaThumbnailControlador.class.getResourceAsStream("experienciaThumbnail.fxml"));
        ExperienciaThumbnailControlador experienciaThumbnailControlador = fxmlLoader.getController();
        experienciaThumbnailControlador.init(experiencia);
        experienciaContainer.setId("experience");
        experiences.add(experienciaContainer,0,currentY); // Adds the container to the scene
    }

    private void makeFrosty(Node container, Pane targetPane){
        FrostyEffect effect = new FrostyEffect(); // Instantiates the effect. The parameters are optional and default to (0.5, 10)
        FrostyBox box = new FrostyBox(effect, container); // Instantiates a container with frosty effect
        box.setAntialiasingLevel(0.4); // See notes below
        box.setLayoutX(container.layoutXProperty().get());
        box.setLayoutY(container.layoutYProperty().getValue());
        targetPane.getChildren().remove(1);
        targetPane.getChildren().add(1,box); // Adds the container to the scene
    }

    private void imagesRotation(int direction, int current){
        if(current==0 && direction<0){
            current=images.size()-1;
            this.currentPos=current;
            head.setBackground(new Background(new BackgroundImage(images.get(current), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        } else if(current==images.size()-1 && direction>0) {
            current=0;
            head.setBackground(new Background(new BackgroundImage(images.get(current), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        } else {
            head.setBackground(new Background(new BackgroundImage(images.get(current+ direction), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        }
    }

    private static void setFasterScroller(ScrollPane scrollPane) {
        ScrollBar verticalScrollbar = (ScrollBar) scrollPane.lookup(".scroll-bar:vertical");
        double defaultUnitIncrement = verticalScrollbar.getUnitIncrement();
        verticalScrollbar.setUnitIncrement(defaultUnitIncrement * 100);
    }

    public void nextImgHighlight(MouseEvent mouseEvent) {
        nextBtn.setStyle("-fx-background-color: rgba(37,37,37,0.37)");
    }

    public void prevImgHighlight(MouseEvent mouseEvent) {
        prevBtn.setStyle("-fx-background-color: rgba(37,37,37,0.37)");
    }

    public void prevImgHighlightOff(MouseEvent mouseEvent) {
        prevBtn.setStyle("-fx-background-color: rgba(0,0,0,0)");
    }

    public void nextImgHighlightOff(MouseEvent mouseEvent) {
        nextBtn.setStyle("-fx-background-color: rgba(0,0,0,0)");
    }
}
