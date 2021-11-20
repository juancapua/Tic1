package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.UsuarioMgr;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.persistance.ExperienciaRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class VerExperienciaControlador {

    private Experiencia experiencia;
    @Autowired
    private ExperienciaRepository experienciaRepository;
    @Autowired
    private UsuarioRepository usuarios;



    @FXML
    private AnchorPane base;

    @FXML
    private Text experiencia_nombre;

    @FXML
    private Text destino_nombre;

    @FXML
    private Text fav_icon;

    private boolean faved = false;

    private Usuario usuario;

    public void init(Experiencia experiencia) {
        this.experiencia = experiencia;
        InputStream x = new ByteArrayInputStream(experiencia.getImagen());
        base.setBackground(new Background(new BackgroundImage(new Image(x), BackgroundRepeat.NO_REPEAT, null, BackgroundPosition.CENTER,null)));

        this.usuario = Controlador.usuario;
        experiencia_nombre.setText(experiencia.getNombre());
        destino_nombre.setText(experiencia.getDestino().getNombre());
        if(this.usuario.getFavoritos().contains(experiencia)){
            faved=true;
            fav_icon.setText("\uE735");
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
            usuario.getFavoritos().removeIf(experiencia1 -> (experiencia1.getId()==experiencia.getId()));
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

