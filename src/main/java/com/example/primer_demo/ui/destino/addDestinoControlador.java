package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.CiudadMgr;
import com.example.primer_demo.business.DepartamentoMgr;
import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.EtiquetaMgr;
import com.example.primer_demo.business.entities.Ciudad;
import com.example.primer_demo.business.entities.Departamento;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class addDestinoControlador implements Initializable {

    @Autowired
    private DepartamentoMgr departamentoMgr;

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private DestinoMgr destinoMgr;

    @Autowired
    private EtiquetaMgr etiquetaMgr;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> departamento;

    @FXML
    private TextField horario_apertura;

    @FXML
    private TextField horario_cierre;

    @FXML
    private TextField txtAforo;

    @FXML
    private TextField txtContacto;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField descripcion;

    @FXML
    private VBox vbox;

    private Operador operador;

    @FXML
    private Button elegirImagenes;

    private byte[] imagenes;

    public void setOperador(Operador operador){
        this.operador = operador;
    }

    @FXML
    public void addDestino(ActionEvent event){
        if(txtNombre.getText() == null || txtNombre.getText().equals("") || txtContacto.getText() == null || txtContacto.getText().equals("") || txtAforo.getText() == null || txtAforo.equals("")
                || horario_apertura.getText() == null || horario_apertura.getText().equals("") || horario_cierre.getText() == null || horario_cierre.getText().equals("")
                || txtDireccion.getText() == null || txtDireccion.getText().equals("") || departamento.getValue() == null || departamento.getValue().equals("")){
            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");
        }
        if(destinoMgr.existeDestino(txtNombre.getText())){
            showAlert("Error", "El destino ya esta ingresado en el sistema");
        }else{

            try {
                String nombre = txtNombre.getText();
                String contacto = txtContacto.getText();
                Integer aforo = Integer.parseInt(txtAforo.getText());
                LocalTime horario_aper = LocalTime.parse(horario_apertura.getText());
                LocalTime horario_cie = LocalTime.parse(horario_cierre.getText());
                String direccion = txtDireccion.getText();
                Departamento departamento_elejido = departamentoMgr.traerDepartamento(departamento.getValue());
                Set<Etiqueta> etiquetas = new HashSet<>();
                Operador operador = this.operador;
                String desc = descripcion.getText();
                byte[] nuevaImagen = this.imagenes;

                for(Node node:vbox.getChildren()){
                    CheckBox checkBox = (CheckBox) node;
                    if(checkBox.isSelected()){
                        etiquetas.add((Etiqueta) checkBox.getUserData());
                    }
                }

                try {
                    destinoMgr.agregarDestino(nombre,contacto,aforo,horario_aper,horario_cie,direccion,departamento_elejido,operador,etiquetas, desc, nuevaImagen);
                    showAlert("Destino agregado", "Se agrego con exito el destino!");

                    close(event);
                } catch (InvalidInformation invalidInformation) {
                    invalidInformation.printStackTrace();
                }


            } catch (NumberFormatException | IOException e) {
                showAlert("Error", "Los datos ingresados son incorrectos");
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterable<Departamento> departamentos = departamentoMgr.allDepartamentos();
        for(Departamento x: departamentos){
            departamento.getItems().add(x.getNombre_pk());
        }
        for(Etiqueta x: etiquetaMgr.listaEtiquetas()){
            CheckBox interestCheckBox = new CheckBox(x.getNombre());
            interestCheckBox.setUserData(x);
            vbox.getChildren().add(interestCheckBox);
        }
    }

    @FXML
    void close(javafx.event.ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
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

    @FXML
    private void openFileDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("JPEG","*,jpeg"));
        try {
            File archivo = fileChooser.showOpenDialog(new Stage());
            if(archivo != null){
                Path path = Paths.get(archivo.getAbsolutePath());
                this.imagenes = Files.readAllBytes(path);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
