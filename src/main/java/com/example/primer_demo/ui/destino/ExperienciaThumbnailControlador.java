package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Experiencia;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class ExperienciaThumbnailControlador {

    private Experiencia destino;

    @FXML
    private Button reservaBtn;

    @FXML
    private Text priceIndicator;

    @FXML
    private Text title;

    @FXML
    private Text desc;

    public void init(Experiencia experiencia){
        title.setText(experiencia.getNombre());
        desc.setText(experiencia.getDescripcion());
    }

}
