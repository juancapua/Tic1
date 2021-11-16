package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.persistance.ExperienciaRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerExperienciaControlador {

    private Experiencia experiencia;
    private ExperienciaRepository experienciaRepository;
    private UsuarioRepository usuarios;


    @FXML
    private Text experiencia_nombre;

    @FXML
    private Text destino_nombre;

    @FXML
    private Text fav_icon;

    private boolean faved = false;

    private Usuario usuario;

    public void init(Experiencia experiencia, Usuario usuario) {
        this.experiencia = experiencia;
        experiencia_nombre.setText(experiencia.getNombre());
        destino_nombre.setText(experiencia.getDestino().getNombre());
        if(usuario.getFavoritos().contains(experiencia)){
            faved=true;
        }
    }

    public void clicked(){
        if(!faved) {
            fav_icon.setText("\uE735");
            faved = true;
            usuario.getFavoritos().add(experiencia);

        } else {
            fav_icon.setText("\uE734");
            faved = false;
            usuario.getFavoritos().remove(experiencia);
        }
        usuarios.save(usuario);
    }

    public void mouseEnter(){
        if(!faved) {
            fav_icon.setText("\uE735");
        }
    }

    public void mouseExit(){
        if(!faved) {
            fav_icon.setText("\uE734");
        }
    }

    public void reservar(ActionEvent actionEvent) throws IOException, InterruptedException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(VerExperienciaControlador.class.getResourceAsStream("reserva.fxml"));
        HacerReservaControlador reservaControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        reservaControlador.init(experiencia);
    }
}

