package com.example.primer_demo.ui.Inicio;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.ui.destino.DestinoControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class miniaturaDestinoControlador {

    @FXML
    private ImageView imageView;

    @FXML
    private Label titulo;

    @FXML
    private Label desc;

    private Destino destino;

    private Parent root;

    AnchorPane anchorPane;
    void setAnchorPane(AnchorPane pane){
        this.anchorPane = pane;
    }


    public void setData(Destino destino){
        this.destino = destino;
        titulo.setText(destino.getNombre());
        desc.setText(destino.getDescripcion());
        desc.setWrapText(true);
        if(destino.getImages().size() > 0){
            InputStream inputStream = new ByteArrayInputStream(destino.getImages().get(0));
            imageView.setImage(new Image(inputStream));
        }

    }


    public void ver(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(DestinoControlador.class.getResourceAsStream("destination.fxml"));
        DestinoControlador destinoControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(DestinoControlador.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        destinoControlador.init(destino);
    }


}
