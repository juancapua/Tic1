package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Entrada;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class EntradaControlador {

    @FXML
    private Text titulo_entrada;

    @FXML
    private Text texto_entrada;

    public void init(Entrada entrada){
        titulo_entrada.setText(entrada.getTitulo());
        texto_entrada.setText(entrada.getTexto());
    }
}
