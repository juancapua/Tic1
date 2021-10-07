package com.example.primer_demo.ui.destino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class DestinoControlador {

    @FXML
    private TextField nombre_destino;

    @FXML
    private TextField nombre_destino_scrolledDown;

    @FXML
    private Rectangle nombre_container;

    @FXML
    private Rectangle nombre_container_scrolledDown;

}
