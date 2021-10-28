package com.example.primer_demo.ui.Inicio;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.persistance.DestinoRespository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.destino.DestinoControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class InicioControlador implements Initializable {

    @Autowired
    private DestinoRespository destinoRespository;

    @Autowired
    private DestinoMgr destinoMgr;

    @Autowired
    private miniaturaDestinoControlador miniaturaDestinoControlador;

    private Parent root;

    private Usuario usuario;

    @FXML
    private Button boton;

    @FXML
    private Button exit;

    @FXML
    private Label texto;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane anchorPane;

    public void setLabel(String x){

        texto.setText(x);
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {

        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(Controlador.class.getResourceAsStream("sample.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();

        showAlert("Nos vemos pronto", "Se ha cerrado sesion correctamente");
    }




    private void close(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public void test(ActionEvent event) throws Exception {
        cargarDestino(destinoRespository.findById(1).get());
    }

     void cargarDestino(Destino destino) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(DestinoControlador.class.getResourceAsStream("destination.fxml"));
        DestinoControlador destinoControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(DestinoControlador.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        destinoControlador.init(destino);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterable<Destino> destinos = destinoMgr.allDestinos();
        int fila = 1;
        for(Destino x: destinos){
            if(x.getHabilitada()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("miniaturaDestino.fxml"));
                fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
                try {
                    AnchorPane pane = fxmlLoader.load();
                    miniaturaDestinoControlador = fxmlLoader.getController();
                    miniaturaDestinoControlador.setData(x);
                    miniaturaDestinoControlador.setAnchorPane(anchorPane);
                    gridPane.addColumn(fila, pane);
                    fila++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
