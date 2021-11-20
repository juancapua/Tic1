package com.example.primer_demo.ui.destino;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.ui.experiencia.VerExperienciaControlador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExperienciaThumbnailControlador {

    @FXML
    private AnchorPane base;

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
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(VerExperienciaControlador.class.getResourceAsStream("experiencia.fxml"));
        VerExperienciaControlador verExperienciaControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        verExperienciaControlador.init(experiencia);
    }


    public void mouseEnter(MouseEvent mouseEvent) {
        base.setStyle("-fx-background-color: rgba(100,100,100,0.5)");
    }

    public void mouseExit(MouseEvent mouseEvent) {
        base.setStyle("-fx-background-color: transparent");
    }
}
