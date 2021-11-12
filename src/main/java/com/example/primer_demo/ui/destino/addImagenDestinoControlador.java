package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.entities.Destino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class addImagenDestinoControlador {

    @Autowired
     private DestinoMgr destinoMgr;

    private Destino destino;

    private List<byte[]> listaImagenes;

    public void setDestino(Destino destino){
        this.destino = destino;
        this.listaImagenes = new ArrayList<byte[]>();
    }

    @FXML
    void agregarImagen(ActionEvent event){
        destinoMgr.agregarImagenes(this.destino, this.listaImagenes);
        showAlert("Buenas noticias", "El archivo seleccionado se guardo correctamente");
        close(event);
    }

    @FXML
    void openFileDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("JPEG","*,jpeg"),
                new FileChooser.ExtensionFilter("JFIF","*,jfif"));
        try {
            List<File> archivo = fileChooser.showOpenMultipleDialog(new Stage());
            if(archivo != null){
                for(File x: archivo){
                    Path path = Paths.get(x.getAbsolutePath());
                    this.listaImagenes.add(Files.readAllBytes(path));
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    @FXML
    void close(javafx.event.ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
