package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.ManejadorDeCargaDePantalla;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.ui.experiencia.ExperienciaControlador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExperienciaThumbnailControlador {

    private Experiencia experiencia;

    @FXML
    private Button reservaBtn;

    @FXML
    private Text priceIndicator;

    @FXML
    private Text title;

    @FXML
    private Text desc;

    public void init(Experiencia experiencia){
        this.experiencia = experiencia;
        title.setText(experiencia.getNombre());
        desc.setText(experiencia.getDescripcion());
    }

    public void abrirExperiencia() throws IOException {
        ManejadorDeCargaDePantalla.cargarPantallaAnchoPane("experiencia.fxml", ExperienciaControlador.class);
    }


}
