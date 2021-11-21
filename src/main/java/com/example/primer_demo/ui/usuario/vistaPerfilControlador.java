package com.example.primer_demo.ui.usuario;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class vistaPerfilControlador {

    private Usuario usuario;

    private Parent root;

    @FXML
    private TableColumn<Reserva, String> columnaDireccion;

    @FXML
    private TableColumn<Reserva, Date> columnaFecha;

    @FXML
    private TableColumn<Experiencia, String> columnaReservas;

    @FXML
    private Label documentotxt;

    @FXML
    private Label mailtxt;

    @FXML
    private Label nacimientotxt;

    @FXML
    private Label nombretxt;

    @FXML
    private Label paistxt;

    @FXML
    private TableView<Experiencia> tabla;

    @FXML
    private Label vacunadotxt;

    @FXML
    private VBox vbox;

    private ObservableList<Experiencia> listaObservable;

    public void setUsuario(Usuario usuario){
        this.usuario =usuario;
        nombretxt.setText("Nombre: " + this.usuario.getNombreDeUsuario());
        mailtxt.setText("Mail: " + this.usuario.getMail());
        documentotxt.setText("Documento: " + this.usuario.getDocumento());
        paistxt.setText("Pais: " + this.usuario.getPais());
        nacimientotxt.setText("Nacimiento: " + this.usuario.getFechaNac());
        if(this.usuario.getVacunado().equals(true)){
            vacunadotxt.setText("Vacuna: Si");
        }else{
            vacunadotxt.setText("Vacuna: No");
        }
        for(Etiqueta x: this.usuario.getEtiquetas()){
            Label label = new Label("-" + x.getNombre());
            label.setFont(new Font("Britannic bold", 13));
            label.setTextFill(Color.web("#ffffff"));
            vbox.getChildren().add(label);
        }
        Set<Experiencia> experiencias = new HashSet<>();
        for(Reserva x: this.usuario.getReservas()){
            experiencias.add(x.getExperiencia());
        }

        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(experiencias);
        tabla.setItems(listaObservable);
        columnaReservas.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @FXML
    void volverInicio(ActionEvent event) throws IOException {

        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(InicioControlador.class.getResourceAsStream("inicio.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        InicioControlador inicioControlador = fxmlLoader.getController();
        inicioControlador.setUsuario(this.usuario);
        stage.show();

    }

    @FXML
    void modificarPerfil(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(editUsuarioControlador.class.getResourceAsStream("editUsuario.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        editUsuarioControlador editUsuarioControlador = fxmlLoader.getController();
        editUsuarioControlador.setUsuario(this.usuario);
        stage.show();
    }




    private void close(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }



}
