package com.example.primer_demo.ui.destino;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.ExperienciaMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.UsuarioOperador;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import com.example.primer_demo.ui.experiencia.addExperienciaControlador;
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
import java.util.Set;

@Component
public class vistaOperadorDestinoControlador {

    private Destino destino;

    private Parent root;

    @Autowired
    private ExperienciaMgr experienciaMgr;

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
        Label label = new Label(this.destino.getDescripcion());
        label.setFont(new Font("Britannic bold", 13));
        label.setTextFill(Color.web("#ffffff"));
        label.setWrapText(true);
        vbox2.getChildren().add(label);

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

    @FXML
    private VBox vbox2;

    @FXML
    void bloquearExperiencia(ActionEvent event){
        Experiencia seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            experienciaMgr.bloquearExperiencia(seleccion);

            Set<Experiencia> experiencias = this.destino.getExperiencias();
            listaObservable.removeAll(listaObservable);
            listaObservable.addAll(experiencias);
        }
    }

    @FXML
    void habilitarExperiencia(ActionEvent event){
        Experiencia seleccion = tabla.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            experienciaMgr.habilitarExperiencia(seleccion);

            Set<Experiencia> experiencias = this.destino.getExperiencias();
            listaObservable.removeAll(listaObservable);
            listaObservable.addAll(experiencias);
        }
    }

    @FXML
    void addExperiencia(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(addExperienciaControlador.class.getResourceAsStream("addExperiencia.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        addExperienciaControlador addExperienciaControlador = fxmlLoader.getController();
        addExperienciaControlador.setDestino(this.destino);
        stage.show();
    }

    @FXML
    void close(javafx.event.ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
