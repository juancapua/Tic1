package com.example.primer_demo.ui.usuarioOperador;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.UsuarioOperadorMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.entities.UsuarioOperador;
import com.example.primer_demo.ui.operador.operadorControlador;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.hql.spi.id.cte.AbstractCteValuesListBulkIdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class vistaEmpleadoControlador {

    @Autowired
    private UsuarioOperadorMgr usuarioOperadorMgr;

    private Parent root;

    private Operador operador;

    ObservableList<UsuarioOperador> listaObservable;

    @FXML
    private TableColumn<UsuarioOperador, String> columnaEmpleados;

    @FXML
    private TableColumn<UsuarioOperador, String> columnaEstado;

    @FXML
    private TableView<UsuarioOperador> tabla;

    public void setOperador(Operador operador){
        this.operador = operador;
        Set<UsuarioOperador> usuarios = operador.getUsuarioOperadorList();
        listaObservable = FXCollections.observableArrayList();
        listaObservable.addAll(usuarios);
        tabla.setItems(listaObservable);
        columnaEmpleados.setCellValueFactory(new PropertyValueFactory<>("nombreDeUsuario"));
        columnaEstado.setCellValueFactory(cellData -> {
            boolean estado = cellData.getValue().getEstado();
            String estadoAsString;
            if (!estado) {
                estadoAsString = "Bloqueado";
            } else {
                estadoAsString = "Habilitado";
            }
            return new ReadOnlyStringWrapper(estadoAsString);});
    }

    @FXML
    void bloquearEmpleado(ActionEvent event){
        UsuarioOperador empleado = tabla.getSelectionModel().getSelectedItem();
        if(empleado != null){
            usuarioOperadorMgr.bloaquear(empleado);

            Set<UsuarioOperador> empleados = operador.getUsuarioOperadorList();
            listaObservable.removeAll(listaObservable);
            listaObservable.addAll(empleados);
        }
    }

    @FXML
    void habilitarEmpleado(ActionEvent event){
        UsuarioOperador empleado = tabla.getSelectionModel().getSelectedItem();
        if(empleado != null){
            usuarioOperadorMgr.habilitar(empleado);

            Set<UsuarioOperador> empleados = operador.getUsuarioOperadorList();
            listaObservable.removeAll(listaObservable);
            listaObservable.addAll(empleados);
        }
    }

    @FXML
    void volver(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(operadorControlador.class.getResourceAsStream("operador.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        operadorControlador operadorControlador = fxmlLoader.getController();
        operadorControlador.setOperador(this.operador.getNombreDeUsuario());
        stage.show();
    }

    @FXML
    void close(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
