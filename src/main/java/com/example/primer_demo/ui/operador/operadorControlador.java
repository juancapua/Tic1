package com.example.primer_demo.ui.operador;


import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.OperadorMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.admin.adminControlador;
import com.example.primer_demo.ui.destino.addDestinoControlador;
import com.example.primer_demo.ui.destino.addImagenDestinoControlador;
import com.example.primer_demo.ui.destino.editDestinoControlador;
import com.example.primer_demo.ui.destino.vistaOperadorDestinoControlador;
import com.example.primer_demo.ui.usuarioOperador.vistaEmpleadoControlador;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class operadorControlador {

    @Autowired
    private OperadorMgr operadorMgr;

    @Autowired
    private DestinoMgr destinoMgr;

    private Parent root;

    @Autowired
    private OperadorRepository operadorRepository;


    @FXML
    private Button btnVolver;

    @FXML
    private Button btnAgregar;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtContrasena;

    @FXML
    private TextField txtConfContrasena;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    private Operador operador;

    @FXML
    private TableColumn<Destino, String> columnaDetinos;

    @FXML
    private TableColumn<Destino, String> columnaEstado;

    @FXML
    private TableView<Destino> tabla;

    ObservableList<Destino> listaObservable;

    public void setOperador(String nombreDeUsuario){
        this.operador = operadorRepository.findByNombreDeUsuario(nombreDeUsuario);
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
            listaObservable.removeAll(listaObservable);
            listaObservable.addAll(destinos);
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
    void cambiarDestino(ActionEvent event) throws IOException {
        Destino seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

            root = fxmlLoader.load(editDestinoControlador.class.getResourceAsStream("editDestino.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("images/logo_final.png"));
            stage.setResizable(false);
            editDestinoControlador editDestinoControlador = fxmlLoader.getController();
            editDestinoControlador.setDestino(seleccion);
            stage.show();
        }
    }

    @FXML
    void desbloquearDestino(ActionEvent event){
        Destino seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            destinoMgr.desbloquearDestino(seleccion);

            Set<Destino> destinos = operador.getDestinos();
            listaObservable.removeAll(listaObservable);
            listaObservable.addAll(destinos);}
    }

    @FXML
    void close(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addDestino(Event event) throws IOException{
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

    @FXML
    void agregarImagen(ActionEvent event) throws IOException {
        Destino seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

            root = fxmlLoader.load(addImagenDestinoControlador.class.getResourceAsStream("addImagenDestino.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("images/logo_final.png"));
            stage.setResizable(false);
            addImagenDestinoControlador addImagenDestinoControlador = fxmlLoader.getController();
            addImagenDestinoControlador.setDestino(seleccion);
            stage.show();
        }
    }



    @FXML
    void verEmpleados(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(vistaEmpleadoControlador.class.getResourceAsStream("vistaEmpleados.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        vistaEmpleadoControlador vistaEmpleadoControlador = fxmlLoader.getController();
        vistaEmpleadoControlador.setOperador(this.operador);
        stage.show();
    }


    @FXML
    void addOperador(ActionEvent event) {
        if (txtUsuario.getText() == null || txtUsuario.getText().equals("") ||
                txtCorreo.getText() == null || txtCorreo.getText().equals("") ||
                txtContrasena.getText() == null || txtContrasena.getText().equals("") ||
                txtConfContrasena.getText() == null || txtConfContrasena.getText().equals("") ||
                txtTelefono.getText() == null || txtTelefono.getText().equals("")||
                txtDireccion.getText() == null || txtDireccion.getText().equals("")

        ) {

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        }if(!txtContrasena.getText().equals(txtConfContrasena.getText())){
            showAlert("Datos incorrectos", "Las contraseñas no coinciden");

        } else {

            try {

                String usuario = txtUsuario.getText();
                String correo = txtCorreo.getText();
                String contrasena = txtContrasena.getText();
                int telefono = Integer.parseInt(txtTelefono.getText());
                String direccion = txtDireccion.getText();


                try {

                    operadorMgr.agregarOperador(usuario, correo, contrasena, telefono, direccion);

                    showAlert("Cliente agregado", "Se agrego con exito el operador!");

                    close(event);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

                    root = fxmlLoader.load(adminControlador.class.getResourceAsStream("admin.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.getIcons().add(new Image("images/logo_final.png"));
                    stage.setResizable(false);
                    stage.show();
                } catch (InvalidInformation invalidInformation) {

                    showAlert(
                            "Informacion invalida !",
                            "Se encontro un error en los datos ingresados.");
                }catch (UsuarioAlreadyExist usuarioAlreadyExist){
                    showAlert(
                            "Documento ya registrado !",
                            "El nombre indicado ya ha sido registrado en el sistema.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {

                showAlert(
                        "Datos incorrectos !",
                        "El documento no tiene el formato esperado (numerico).");

            }
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
    void volverAtras(ActionEvent event) throws IOException {
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

    private void clean() {
        txtUsuario.setText(null);
        txtCorreo.setText(null);
        txtConfContrasena.setText(null);
        txtContrasena.setText(null);
    }

    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
}
