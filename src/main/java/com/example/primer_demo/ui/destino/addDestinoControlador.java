package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.CiudadMgr;
import com.example.primer_demo.business.DepartamentoMgr;
import com.example.primer_demo.business.EtiquetaMgr;
import com.example.primer_demo.business.entities.Ciudad;
import com.example.primer_demo.business.entities.Departamento;
import com.example.primer_demo.business.entities.Etiqueta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class addDestinoControlador implements Initializable {

    @Autowired
    private DepartamentoMgr departamentoMgr;

    @Autowired
    private CiudadMgr ciudadMgr;

    @Autowired
    private EtiquetaMgr etiquetaMgr;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> ciudad;

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
    private VBox vbox;

    @FXML
    void agregaCiudades(ActionEvent event){
        Iterable<Ciudad> ciudades = departamentoMgr.buscarCiudades(departamento.getValue());
        for(Ciudad y: ciudades){
            ciudad.getItems().add(y.getNombre_pk());
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
}
