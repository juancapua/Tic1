package com.example.primer_demo.ui.destino;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.ManejadorDeCargaDePantalla;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.ui.experiencia.ExperienciaControlador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

    private Usuario usuario;

    public void init(Experiencia experiencia, Usuario usuario){
        this.experiencia = experiencia;
        title.setText(experiencia.getNombre());
        desc.setText(experiencia.getDescripcion());
        this.usuario = usuario;
    }


    public void abrirExperiencia() throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(ExperienciaControlador.class.getResourceAsStream("experiencia.fxml"));
        ExperienciaControlador experienciaControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        experienciaControlador.init(experiencia, usuario);
    }


}
