package com.example.primer_demo.ui.admin;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.OperadorMgr;
import com.example.primer_demo.business.UsuarioOperadorMgr;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.entities.UsuarioOperador;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioOperadorRepository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.operador.VistaAdminOperadorControlador;
import com.example.primer_demo.ui.operador.operadorControlador;
import com.example.primer_demo.ui.usuario.UsuarioControlador;
import com.example.primer_demo.ui.usuarioOperador.UsuarioOperadorControlador;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class adminControlador implements Initializable {

    private Parent root;

    @Autowired
    private OperadorMgr operadorMgr;

    @Autowired
    private UsuarioOperadorMgr usuarioOperadorMgr;

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private UsuarioOperadorRepository usuarioOperadorRepository;

    @FXML
    private TableView<Operador> tabla;

    @FXML
    private TableColumn<Operador, String> nombreOperador;

    @FXML
    private TableColumn<Operador, String> estadoOperador;

    @FXML
    private Button bloquearOperador;

    @FXML
    private Button habilitarOperador;

    ObservableList<Operador> listaObservable;

    @FXML
    void agregarOperador(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(operadorControlador.class.getResourceAsStream("createOperador.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();


    }

    @FXML
    void agregarUsuarioOperador(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(UsuarioOperadorControlador.class.getResourceAsStream("usuarioOperador.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void bloquearOperador(ActionEvent event){

        Operador operador = tabla.getSelectionModel().getSelectedItem();
        if(operador != null) {
            operadorMgr.bloaquear(operador);
            for(UsuarioOperador x: usuarioOperadorRepository.findAllByOperador(operador)){
                usuarioOperadorMgr.bloaquear(x);
            }
            List<Operador> operadores = (List<Operador>) operadorRepository.findAll();
            listaObservable = FXCollections.observableArrayList();
            listaObservable.addAll(operadores);
            tabla.setItems(listaObservable);
            nombreOperador.setCellValueFactory(new PropertyValueFactory<>("nombreDeUsuario"));
            estadoOperador.setCellValueFactory(cellData -> {
                boolean estado = cellData.getValue().getEstado();
                String estadoAsString;
                if (!estado) {
                    estadoAsString = "Bloqueado";
                } else {
                    estadoAsString = "Habilitado";
                }
                return new ReadOnlyStringWrapper(estadoAsString);
            });
        }

    }

    @FXML
    void habilitarOperador(ActionEvent event){

        Operador operador = tabla.getSelectionModel().getSelectedItem();
        if(operador != null) {
            operadorMgr.habilitar(operador);

            List<Operador> operadores = (List<Operador>) operadorRepository.findAll();
            listaObservable = FXCollections.observableArrayList();
            listaObservable.addAll(operadores);
            tabla.setItems(listaObservable);
            nombreOperador.setCellValueFactory(new PropertyValueFactory<>("nombreDeUsuario"));
            estadoOperador.setCellValueFactory(cellData -> {
                boolean estado = cellData.getValue().getEstado();
                String estadoAsString;
                if (!estado) {
                    estadoAsString = "Bloqueado";
                } else {
                    estadoAsString = "Habilitado";
                }
                return new ReadOnlyStringWrapper(estadoAsString);
            });
        }


    }

    @FXML
    void verOperador(ActionEvent event) throws IOException {

        Operador operador = tabla.getSelectionModel().getSelectedItem();
        if(operador != null) {
            close(event);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

            root = fxmlLoader.load(VistaAdminOperadorControlador.class.getResourceAsStream("vistaAdminOperador.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("images/logo_final.png"));
            stage.setResizable(false);
            VistaAdminOperadorControlador controlador = fxmlLoader.getController();
            controlador.setOperador(operador);
            stage.show();
        }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Operador> operadores = (List<Operador>) operadorRepository.findAll();
        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(operadores);
        tabla.setItems(listaObservable);
        nombreOperador.setCellValueFactory(new PropertyValueFactory<>("nombreDeUsuario"));
        estadoOperador.setCellValueFactory(cellData -> {
            boolean estado = cellData.getValue().getEstado();
            String estadoAsString;
            if(!estado) {
                estadoAsString = "Bloqueado";
            }
            else {
                estadoAsString = "Habilitado";
            }
            return new ReadOnlyStringWrapper(estadoAsString);
        });
    }
}
