package com.example.primer_demo.ui.usuarioOperador;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioOperadorRepository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.destino.addDestinoControlador;
import com.example.primer_demo.ui.destino.vistaOperadorDestinoControlador;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class HomeUsuarioOperador{

    private Parent root;

    private Operador operador;

    @Autowired
    private DestinoMgr destinoMgr;


    @Autowired
    private UsuarioOperadorRepository usuarioOperadorRepository;

    @FXML
    private TableColumn<Destino, String> columnaDetinos;

    @FXML
    private TableColumn<Destino, String> columnaEstado;

    @FXML
    private TableView<Destino> tabla;

    ObservableList<Destino> listaObservable;

    public void setOperador(String nombreDeUsuario){
        this.operador = usuarioOperadorRepository.findById(nombreDeUsuario).get().getOperador();
        Set<Destino> destinos = operador.getDestinos();
        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(destinos);
        tabla.setItems(listaObservable);
        columnaDetinos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaEstado.setCellValueFactory(cellData -> {
            boolean estado = cellData.getValue().getHabilitada();
            String estadoAsString;
            if (!estado) {
                estadoAsString = "Bloqueado";
            } else {
                estadoAsString = "Habilitado";
            }
            return new ReadOnlyStringWrapper(estadoAsString);});
    }

    @FXML
    void bloquearDestino(ActionEvent event){
        Destino seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            destinoMgr.bloquearDestino(seleccion);

            Set<Destino> destinos = operador.getDestinos();
            listaObservable = FXCollections.observableArrayList();
            listaObservable.addAll(destinos);
            tabla.setItems(listaObservable);
            columnaDetinos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columnaEstado.setCellValueFactory(cellData -> {
                boolean estado = cellData.getValue().getHabilitada();
                String estadoAsString;
                if (!estado) {
                    estadoAsString = "Bloqueado";
                } else {
                    estadoAsString = "Habilitado";
                }
                return new ReadOnlyStringWrapper(estadoAsString);});
        }
    }

    @FXML
    void desbloquearDestino(ActionEvent event){
        Destino seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            destinoMgr.desbloquearDestino(seleccion);

            Set<Destino> destinos = operador.getDestinos();
            listaObservable = FXCollections.observableArrayList();
            listaObservable.addAll(destinos);
            tabla.setItems(listaObservable);
            columnaDetinos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columnaEstado.setCellValueFactory(cellData -> {
                boolean estado = cellData.getValue().getHabilitada();
                String estadoAsString;
                if (!estado) {
                    estadoAsString = "Bloqueado";
                } else {
                    estadoAsString = "Habilitado";
                }
                return new ReadOnlyStringWrapper(estadoAsString);});
        }
    }

    @FXML
    void verDestino(ActionEvent event) throws IOException {
        Destino seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

            root = fxmlLoader.load(vistaOperadorDestinoControlador.class.getResourceAsStream("vistaOperadorDestino.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("images/logo_final.png"));
            stage.setResizable(false);
            vistaOperadorDestinoControlador vistaOperadorDestinoControlador = fxmlLoader.getController();
            vistaOperadorDestinoControlador.setDestino(seleccion);
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

    @FXML
    void addDestino(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(addDestinoControlador.class.getResourceAsStream("addDestino.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        addDestinoControlador addDestinoControlador = fxmlLoader.getController();
        addDestinoControlador.setOperador(this.operador);
        stage.show();

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

}
