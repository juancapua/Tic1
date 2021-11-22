package com.example.primer_demo.ui.Inicio;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.DepartamentoMgr;
import com.example.primer_demo.business.DestinoMgr;
import com.example.primer_demo.business.PaisMgr;
import com.example.primer_demo.business.entities.*;
import com.example.primer_demo.persistance.DestinoRespository;
import com.example.primer_demo.persistance.PaisRepository;
import com.example.primer_demo.ui.Controlador;
import com.example.primer_demo.ui.destino.ExperienciaThumbnailControlador;
import com.example.primer_demo.ui.destino.VerDestinoControlador;
import com.example.primer_demo.ui.usuario.vistaPerfilControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class InicioControlador {

    @Autowired
    private DestinoRespository destinoRespository;

    @Autowired
    private DestinoMgr destinoMgr;

    @Autowired
    private DepartamentoMgr departamentoMgr;


    private Parent root;

    public static Usuario usuario;

    @FXML
    private Button boton;

    @FXML
    private Button exit;

    @FXML
    private Button favoritos;

    @FXML
    private Label texto;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField busqueda;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> filtroDepartamento;

    private Boolean departamentofiltro = false;


    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
        texto.setText(this.usuario.getNombreDeUsuario());

        Set<String> etiquetasUsuario = new HashSet<>();
        for(Etiqueta x: this.usuario.getEtiquetas()){
            etiquetasUsuario.add(x.getNombre());
        }

        Iterable<Destino> destinos = destinoMgr.allDestinos();

        Set<Destino> destinosOrdenados = new HashSet<>();
        for(Destino y: destinos){
            for(Etiqueta destinoEtiquetas: y.getEtiquetas()){
                if(etiquetasUsuario.contains(destinoEtiquetas.getNombre())){
                    destinosOrdenados.add(y);
                }
            }
        }
        for(Destino destino:destinos){
            if(!destinosOrdenados.contains(destino)){
                destinosOrdenados.add(destino);
            }
        }
        for(Departamento x: departamentoMgr.allDepartamentos()){
            filtroDepartamento.getItems().add(x.getNombre_pk());
        }
        filtroDepartamento.getItems().add("No filter");

        int fila = 1;
        for(Destino x: destinosOrdenados){
            if(x.getHabilitada()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                try {
                    AnchorPane pane = fxmlLoader.load(miniaturaDestinoControlador.class.getResourceAsStream("miniaturaDestino.fxml"));
                    miniaturaDestinoControlador miniatura;
                    miniatura = fxmlLoader.getController();
                    miniatura.setData(x);
                    miniatura.setAnchorPane(anchorPane);
                    gridPane.addRow(fila, pane);

                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                    fila++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {

        gridPane.getChildren().removeAll();
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
    void filtrarDepartamento(ActionEvent event){
        if(!filtroDepartamento.getValue().equals("No filter")) {
            departamentofiltro = true;
            Iterable<Destino> destinos = destinoMgr.filtroPorDepartamento(departamentoMgr.traerDepartamento(filtroDepartamento.getValue()));
            gridPane.getChildren().clear();

            Usuario usuario = Controlador.usuario;
            int fila = 1;
            for (Destino x : destinos) {
                if (x.getHabilitada()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    try {
                        AnchorPane pane = fxmlLoader.load(miniaturaDestinoControlador.class.getResourceAsStream("miniaturaDestino.fxml"));
                        miniaturaDestinoControlador miniatura;
                        miniatura = fxmlLoader.getController();
                        miniatura.setData(x);
                        miniatura.setAnchorPane(anchorPane);
                        gridPane.addRow(fila, pane);

                        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                        fila++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            departamentofiltro = false;
            this.setUsuario(usuario);
            if(busqueda.getText() != null && !busqueda.getText().equals("")){
                gridPane.getChildren().clear();

                Iterable<Destino> destinosFiltrados = destinoMgr.filtroDeBusqueda(busqueda.getText());
                Usuario usuario = Controlador.usuario;
                int fila = 1;
                for (Destino x : destinosFiltrados) {
                    if (x.getHabilitada()) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        try {
                            AnchorPane pane = fxmlLoader.load(miniaturaDestinoControlador.class.getResourceAsStream("miniaturaDestino.fxml"));
                            miniaturaDestinoControlador miniatura;
                            miniatura = fxmlLoader.getController();
                            miniatura.setData(x);
                            miniatura.setAnchorPane(anchorPane);
                            gridPane.addRow(fila, pane);

                            gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                            gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                            gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                            gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                            gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                            fila++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
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

    public void test(ActionEvent event) throws Exception {
        System.out.println(destinoRespository.count());
        cargarDestino(destinoRespository.findAll().iterator().next(), usuario);
    }

    @FXML
    void verPerfil(ActionEvent event) throws IOException {
        close(event);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

        root = fxmlLoader.load(vistaPerfilControlador.class.getResourceAsStream("vistaPerfil.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("images/logo_final.png"));
        stage.setResizable(false);
        vistaPerfilControlador vistaPerfilControlador =fxmlLoader.getController();
        vistaPerfilControlador.setUsuario(this.usuario);
        stage.show();
    }


    public void cargarDestino(Destino destino, Usuario usuario) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(VerDestinoControlador.class.getResourceAsStream("destination.fxml"));
        VerDestinoControlador verDestinoControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(VerDestinoControlador.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        verDestinoControlador.init(destino, stage);
     }

     @FXML
     void busquedaDinamica(KeyEvent event){

        if(!departamentofiltro) {
            gridPane.getChildren().clear();

            Iterable<Destino> destinosFiltrados = destinoMgr.filtroDeBusqueda(busqueda.getText());
            Usuario usuario = Controlador.usuario;
            int fila = 1;
            for (Destino x : destinosFiltrados) {
                if (x.getHabilitada()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    try {
                        AnchorPane pane = fxmlLoader.load(miniaturaDestinoControlador.class.getResourceAsStream("miniaturaDestino.fxml"));
                        miniaturaDestinoControlador miniatura;
                        miniatura = fxmlLoader.getController();
                        miniatura.setData(x);
                        miniatura.setAnchorPane(anchorPane);
                        gridPane.addRow(fila, pane);

                        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                        fila++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            gridPane.getChildren().clear();
            Iterable<Destino> destinosFiltrados = destinoMgr.filroDepartamentoYTexto(departamentoMgr.traerDepartamento(filtroDepartamento.getValue()), busqueda.getText());
            Usuario usuario = Controlador.usuario;
            int fila = 1;
            for (Destino x : destinosFiltrados) {
                if (x.getHabilitada()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    try {
                        AnchorPane pane = fxmlLoader.load(miniaturaDestinoControlador.class.getResourceAsStream("miniaturaDestino.fxml"));
                        miniaturaDestinoControlador miniatura;
                        miniatura = fxmlLoader.getController();
                        miniatura.setData(x);
                        miniatura.setAnchorPane(anchorPane);
                        gridPane.addRow(fila, pane);

                        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                        fila++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
     }

    public void abrirFavoritos(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(FavoritosControlador.class.getResourceAsStream("favoritos.fxml"));
        FavoritosControlador favoritosControlador  = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        favoritosControlador.init(usuario,stage);
    }
}
