package com.example.primer_demo.ui;

import com.example.primer_demo.PrimerDemoApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class JavaFXApplication extends Application {


    private Parent root;

    @Override
    public void init() throws Exception {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);
        root = fxmlLoader.load(Controlador.class.getResourceAsStream("sample.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("images/logo_final.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void stop() {
        PrimerDemoApplication.getContext().close();
    }



}
