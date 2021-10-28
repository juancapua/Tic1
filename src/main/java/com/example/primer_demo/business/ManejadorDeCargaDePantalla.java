package com.example.primer_demo.business;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.ui.destino.DestinoControlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ManejadorDeCargaDePantalla {

    public static void cargarPantallaAnchoPane(String fmxlfile, Class controlador) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        AnchorPane container = fxmlLoader.load(controlador.getClass().getResourceAsStream(fmxlfile));
        DestinoControlador destinoControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
