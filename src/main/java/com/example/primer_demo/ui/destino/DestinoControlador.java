package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Destino;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class DestinoControlador {

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

    public void init(Destino destino) {
        setNombre_destino(destino.getNombre());
    }

    private void setNombre_destino(String nombre_destino){
        this.nombre_destino.setText(nombre_destino);
    }

    public void scrollPane(){

    }

}
