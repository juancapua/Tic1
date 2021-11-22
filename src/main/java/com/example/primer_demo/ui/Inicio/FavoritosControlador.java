package com.example.primer_demo.ui.Inicio;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.persistance.ExperienciaRepository;
import com.example.primer_demo.ui.experiencia.VerExperienciaControlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.io.IOException;
import java.util.Set;

@Component
public class FavoritosControlador {

    @Autowired
    private ExperienciaRepository experienciaRepository;

    @FXML
    private ListView<String> lista;

    public void init(Usuario usuario, Stage primaryStage) {
        Set<Experiencia> experiencias = usuario.getFavoritos();
        ObservableList<String> experienciasNombres = FXCollections.observableArrayList();
        for(Experiencia experiencia: experiencias){
            experienciasNombres.add(experiencia.getNombre());
        }
        lista.setItems(experienciasNombres);
        lista.onEditStartProperty().set(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event) {
                Parent root;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
                Experiencia experiencia = experienciaRepository.findByNombre(experienciasNombres.get(lista.getSelectionModel().getSelectedIndex()));
                try {
                    root = fxmlLoader.load(VerExperienciaControlador.class.getResourceAsStream("experiencia.fxml"));
                    VerExperienciaControlador verExperienciaControlador = fxmlLoader.getController();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                    verExperienciaControlador.init(experiencia, stage);
                    primaryStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}

