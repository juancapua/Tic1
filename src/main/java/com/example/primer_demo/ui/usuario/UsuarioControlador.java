package com.example.primer_demo.ui.usuario;

import com.example.primer_demo.business.PaisMgr;
import com.example.primer_demo.business.UsuarioMgr;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.EtiquetaMgr;
import com.example.primer_demo.business.entities.Pais;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.persistance.PaisRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class UsuarioControlador {

    @Autowired
    private UsuarioMgr usuarioMgr;
    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisMgr paisMgr;

    @Autowired
    private EtiquetaMgr etiquetaMgr;

    @FXML
    private Button btnVolver;

    @FXML
    private DatePicker fecha_nac;

    @FXML
    private ComboBox<String> pais;

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
    private TextField txtDocumento;

    @FXML
    private CheckBox esta_vacunado;

    @FXML
    private VBox listaIntereses;


    @FXML
    void close(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize(){
        Iterable<Pais> paises = paisMgr.allPaises();
        for(Pais x: paises){
            pais.getItems().add(x.getNombre());
        }

        for(Etiqueta x: etiquetaMgr.listaEtiquetas()){
            CheckBox interestCheckBox = new CheckBox(x.getNombre());
            interestCheckBox.setUserData(x);
            listaIntereses.getChildren().add(interestCheckBox);
        }

    }

    @FXML
    void addClient(ActionEvent event) {
        if (txtUsuario.getText() == null || txtUsuario.getText().equals("") ||
                txtCorreo.getText() == null || txtCorreo.getText().equals("") ||
                txtContrasena.getText() == null || txtContrasena.getText().equals("") ||
                txtConfContrasena.getText() == null || txtConfContrasena.getText().equals("") ||
                txtDocumento.getText() == null || txtDocumento.getText().equals("") || pais.getValue() == null || pais.getValue().equals("")
                || fecha_nac.getValue() == null || fecha_nac.getValue().equals("")
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
                Long documento = Long.valueOf(txtDocumento.getText());
                String pais_origen = pais.getValue();
                LocalDate fec_nac = fecha_nac.getValue();
                Boolean vacunado = esta_vacunado.isSelected();
                Set<Etiqueta> etiquetas = new HashSet<>();

                for(Node node:listaIntereses.getChildren()){
                    CheckBox checkBox = (CheckBox) node;
                    if(checkBox.isSelected()){
                        etiquetas.add((Etiqueta) checkBox.getUserData());
                    }
                }



                try {

                    usuarioMgr.agregarUsuario(usuario, correo, contrasena, documento, pais_origen, fec_nac, vacunado, etiquetas);

                    showAlert("Cliente agregado", "Se agrego con exito el cliente!");

                    close(event);
                } catch (InvalidInformation invalidInformation) {

                    showAlert(
                            "Informacion invalida !",
                            "Se encontro un error en los datos ingresados.");
                }catch (UsuarioAlreadyExist usuarioAlreadyExist){
                    showAlert(
                            "Documento ya registrado !",
                            "El nombre indicado ya ha sido registrado en el sistema.");
                }

            } catch (NumberFormatException e) {

                showAlert(
                        "Datos incorrectos !",
                        "El documento no tiene el formato esperado (numerico).");

            }
        }

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
