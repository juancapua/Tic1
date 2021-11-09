package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.UsuarioOperador;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class vistaOperadorDestinoControlador {

    private Destino destino;

    public void setDestino(Destino destino){
        this.destino = destino;
        nombretxt.setText("Nombre: " + this.destino.getNombre());
        contactotxt.setText("Contacto: " + this.destino.getContacto());
        aforotxt.setText("Aforo: " + this.destino.getAforo());
        horario_aper_txt.setText("Horario apertura: " + this.destino.getHorario_apertura());
        horario_cie_txt.setText("Horario cierre: " + this.destino.getHorario_cierre());
        departamentotxt.setText("Departamento: " + this.destino.getDepartamento().getNombre_pk());
        for(Etiqueta x: this.destino.getEtiquetas()){
            Label label = new Label("-" + x.getNombre());
            label.setFont(new Font("Britannic bold", 13));
            label.setTextFill(Color.web("#ffffff"));
            vbox.getChildren().add(label);
        }
        descripciontxt.setText("Descripcion: " + this.destino.getDescripcion());

        Set<Experiencia> usuarios = this.destino.getExperiencias();
        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(usuarios);
        tabla.setItems(listaObservable);
        columna_nombres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columna_estado.setCellValueFactory(cellData -> {
            boolean estado = cellData.getValue().getEsta_autorizada();
            String estadoAsString;
            if (!estado) {
                estadoAsString = "Bloqueado";
            } else {
                estadoAsString = "Habilitado";
            }
            return new ReadOnlyStringWrapper(estadoAsString);
        });


    }

    ObservableList<Experiencia> listaObservable;

    @FXML
    private Label aforotxt;

    @FXML
    private VBox vbox;

    @FXML
    private TableColumn<Experiencia, String> columna_estado;

    @FXML
    private TableColumn<Experiencia, String> columna_nombres;

    @FXML
    private Label contactotxt;

    @FXML
    private Label departamentotxt;

    @FXML
    private Label descripciontxt;

    @FXML
    private Label direcciontxt;

    @FXML
    private Label etiquetastxt;

    @FXML
    private Label horario_aper_txt;

    @FXML
    private Label horario_cie_txt;

    @FXML
    private Label nombretxt;

    @FXML
    private TableView<Experiencia> tabla;

}
