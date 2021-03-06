package com.example.primer_demo.ui.operador;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.UsuarioOperadorMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.entities.UsuarioOperador;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioOperadorRepository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.admin.adminControlador;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class VistaAdminOperadorControlador{

    private Parent root;
    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private DestinoMgr destinoMgr;

    @Autowired
    private UsuarioOperadorMgr usuarioOperadorMgr;

    @Autowired
    private UsuarioOperadorRepository usuarioOperadorRepository;

    private Operador operador;

    ObservableList<UsuarioOperador> listaObservable;

    ObservableList<Destino> listaObservable2;

    public VistaAdminOperadorControlador() {
    }

    public void setOperador(Operador operador){
        this.operador = operadorRepository.findByNombreDeUsuario(operador.getNombreDeUsuario());
        txtEmpresa.setText("Empresa: " + operador.getNombreDeUsuario());
        txtMail.setText("Mail: " + operador.getMail());
        txtDireccion.setText("Direccion: " + operador.getDireccion());
        txtTelefono.setText("Telefono: "+ operador.getTelefono());

        Set<UsuarioOperador> usuarios = operador.getUsuarioOperadorList();
        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(usuarios);
        tabla.setItems(listaObservable);
        columnaUsuarios.setCellValueFactory(new PropertyValueFactory<>("nombreDeUsuario"));
        columnaEstado.setCellValueFactory(cellData -> {
            boolean estado = cellData.getValue().getEstado();
            String estadoAsString;
            if (!estado) {
                estadoAsString = "Bloqueado";
            } else {
                estadoAsString = "Habilitado";
            }
            return new ReadOnlyStringWrapper(estadoAsString);
        });

        Set<Destino> destinos = operador.getDestinos();
        listaObservable2 = FXCollections.observableArrayList();
        listaObservable2.addAll(destinos);
        tabla2.setItems(listaObservable2);
        columnaDestino.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaEstadoDestino.setCellValueFactory(cellData -> {
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
    private TableColumn<UsuarioOperador, String> columnaEstado;

    @FXML
    private TableColumn<UsuarioOperador, String> columnaUsuarios;

    @FXML
    private TableView<UsuarioOperador> tabla;

    @FXML
    private TableView<Destino> tabla2;

    @FXML
    private TableColumn<Destino, String> columnaDestino;

    @FXML
    private TableColumn<Destino, String> columnaEstadoDestino;

    @FXML
    private Label txtDireccion;

    @FXML
    private Label txtEmpresa;

    @FXML
    private Label txtMail;

    @FXML
    private Label txtTelefono;

    @FXML
    private Button btnBloquear;

    @FXML
    private Button btnHabilitar;

    @FXML
    void bloquearDestino(ActionEvent event){
        Destino seleccion = tabla2.getSelectionModel().getSelectedItem();
        if(seleccion != null) {
            destinoMgr.bloquearDestino(seleccion);

            Set<Destino> destinos = operador.getDestinos();
            listaObservable2.removeAll(listaObservable2);
            listaObservable2.addAll(destinos);
        }
    }

    @FXML
    void desbloquearDestino(ActionEvent event){
        Destino seleccion = tabla2.getSelectionModel().getSelectedItem();
        if(seleccion != null) {
            destinoMgr.desbloquearDestino(seleccion);

            Set<Destino> destinos = operador.getDestinos();
            listaObservable2.removeAll(listaObservable2);
            listaObservable2.addAll(destinos);
        }
    }

    @FXML
    void atras(ActionEvent event) throws IOException {

        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(adminControlador.class.getResourceAsStream("admin.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        stage.show();
    }


    private void close(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void bloquearUsuarioOperador(ActionEvent event){

        UsuarioOperador usuarioOperador = tabla.getSelectionModel().getSelectedItem();
        usuarioOperadorMgr.bloaquear(usuarioOperador);

        List<UsuarioOperador> usuarios = new ArrayList<>();
        for(UsuarioOperador x: usuarioOperadorRepository.findAllByOperador(operador)){
            usuarios.add(x);
        }
        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(usuarios);
        tabla.setItems(listaObservable);
        columnaUsuarios.setCellValueFactory(new PropertyValueFactory<>("nombreDeUsuario"));
        columnaEstado.setCellValueFactory(cellData -> {
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

    @FXML
    void habilitarUsuarioOperador(ActionEvent event){

        UsuarioOperador usuarioOperador = tabla.getSelectionModel().getSelectedItem();
        usuarioOperadorMgr.habilitar(usuarioOperador);

        List<UsuarioOperador> usuarios = new ArrayList<>();
        for(UsuarioOperador x: usuarioOperadorRepository.findAllByOperador(operador)){
            usuarios.add(x);
        }
        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(usuarios);
        tabla.setItems(listaObservable);
        columnaUsuarios.setCellValueFactory(new PropertyValueFactory<>("nombreDeUsuario"));
        columnaEstado.setCellValueFactory(cellData -> {
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
