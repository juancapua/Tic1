package com.example.primer_demo.ui.usuario;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.ReservaMgr;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import com.example.primer_demo.ui.reserva.editReservaControlador;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class vistaPerfilControlador {

    private Usuario usuario;

    private Parent root;

    @Autowired
    private ReservaMgr reservaMgr;

    @FXML
    private TableColumn<Reserva, String> columnaDireccion;

    @FXML
    private TableColumn<Reserva, Date> columnaFecha;

    @FXML
    private TableColumn<Reserva, String> columnaReservas;

    @FXML
    private TableColumn<Reserva, LocalTime> columnaHora;

    @FXML
    private TableColumn<Reserva, Integer> columnaPersonas;

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
    private TableView<Reserva> tabla;

    @FXML
    private Label vacunadotxt;

    @FXML
    private VBox vbox;

    private ObservableList<Reserva> listaObservable;

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

        List<Reserva> reservas = (List<Reserva>) reservaMgr.allReservasUsuario(this.usuario);

        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(reservas);
        tabla.setItems(listaObservable);
        columnaReservas.setCellValueFactory(cellData -> {
            String nombre = cellData.getValue().getExperiencia().getNombre();
            return new ReadOnlyStringWrapper(nombre);});
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        columnaPersonas.setCellValueFactory(new PropertyValueFactory<>("Personas"));
        columnaDireccion.setCellValueFactory(cellData -> {
            String direccion = cellData.getValue().getExperiencia().getDestino().getDireccion();
            return new ReadOnlyStringWrapper(direccion);});

    }

    @FXML
    void cancelarReserva(ActionEvent event) throws IOException {
        Reserva eleccion = tabla.getSelectionModel().getSelectedItem();
        if(eleccion != null) {
            close(event);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

            root = fxmlLoader.load(editReservaControlador.class.getResourceAsStream("editReserva.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("images/logo_final.png"));
            stage.setResizable(false);
            editReservaControlador editReservaControlador = fxmlLoader.getController();
            editReservaControlador.setReserva(eleccion, this.usuario);
            stage.show();
        }


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
