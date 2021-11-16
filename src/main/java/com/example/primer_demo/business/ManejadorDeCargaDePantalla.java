package com.example.primer_demo.business;

import java.io.IOException;

public abstract class ManejadorDeCargaDePantalla {

    public static Object cargarPantallaAnchoPane(String fmxlfile, Class controlador) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        AnchorPane container = fxmlLoader.load(controlador.getClass().getResourceAsStream(fmxlfile));
        controlador.class claseControlador = fxmlLoader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();*/
        return null;
    }

}
