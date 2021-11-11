package com.example.primer_demo.ui.Inicio;

import com.example.primer_demo.business.entities.Destino;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class miniaturaDestinoControlador {

    @FXML
    private ImageView imageView;

    @FXML
    private Label titulo;

    @FXML
    private Label desc;

    AnchorPane anchorPane;
    void setAnchorPane(AnchorPane pane){
        this.anchorPane = pane;
    }


    public void setData(Destino destino){
        titulo.setText(destino.getNombre());
        desc.setText(destino.getDescripcion());
        desc.setWrapText(true);

        if(destino.getImages().size() > 0){
            InputStream inputStream = new ByteArrayInputStream(destino.getImages().get(0));
            imageView.setImage(new Image(inputStream));
        }

    }




}
