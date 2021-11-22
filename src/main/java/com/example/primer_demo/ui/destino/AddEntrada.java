package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.EntradaMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Entrada;
import com.example.primer_demo.persistance.EntradaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Component
public class AddEntrada {

    private Destino destino;

    @Autowired
    private EntradaMgr entradaMgr;

    @FXML
    private TableColumn<Entrada, String> columnaTexto;

    @FXML
    private TableColumn<Entrada, String> columnaTitulo;


    @FXML
    private ListView<String> listView;


    @FXML
    private TextField titulo;

    @FXML
    private TextArea entrada;

    private String oldTitulo;

    private ObservableList<String> entradas;

    public void init(Destino destino) {
        this.destino = destino;
        entradas = FXCollections.observableArrayList();
        for(Entrada entrada: destino.getEntradas()){
            entradas.add(entrada.getTitulo());
        }
        listView.setItems(entradas);
        listView.onEditStartProperty().set(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event) {
                titulo.setText(listView.getSelectionModel().getSelectedItem());
                entrada.setText(entradaMgr.getEntradaPorNombreYDestino(listView.getSelectionModel().getSelectedItem(),destino.getId()).getTexto());
                oldTitulo = listView.getSelectionModel().getSelectedItem();
            }
        });

    }

    public void guardar(ActionEvent actionEvent) {
        if(listView.getSelectionModel().getSelectedItem() == null && titulo.getText() != null && entrada.getText() !=null){
            entradaMgr.guardarEntrada(titulo.getText(), entrada.getText(),destino);
        } else if(entradaMgr.getEntradaPorNombreYDestino(titulo.getText(),destino.getId())==null){
            entradaMgr.guardarEntrada(titulo.getText(), entrada.getText(), destino);
        } else if(entradaMgr.getEntradaPorNombreYDestino(titulo.getText(),destino.getId()).equals(listView.getSelectionModel().getSelectedItem())){
            entradaMgr.cambiarTitulo(entradaMgr.getEntradaPorNombreYDestino(oldTitulo,destino.getId()),titulo.getText());
        }
    }
}
