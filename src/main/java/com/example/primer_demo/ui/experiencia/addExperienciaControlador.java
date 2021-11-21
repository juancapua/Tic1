package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.ExperienciaMgr;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.ui.destino.vistaOperadorDestinoControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class addExperienciaControlador {

    private Parent root;

    private Destino destino;

    public void setDestino(Destino destino){
        this.destino = destino;
    }

    @Autowired
    private ExperienciaMgr experienciaMgr;

    @FXML
    private CheckBox se_reserva;

    @FXML
    private TextField txtAforo;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtHorario_aper;

    @FXML
    private TextField txtHorario_cie;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField duraciontxt;


    @FXML
    void addExperiencia(ActionEvent event){
        if(txtNombre.getText() == null || txtNombre.getText().equals("") || txtDescripcion.getText() == null || txtDescripcion.getText().equals("") ||
        txtHorario_aper.getText() == null || txtHorario_aper.getText().equals("") || txtHorario_cie.getText() == null || txtHorario_cie.getText().equals("") || duraciontxt.getText() == null ||
        duraciontxt.getText().equals("") || txtAforo.getText() == null || txtAforo.getText().equals("")){
            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");
        }
        else {
            try {
                String nombre = txtNombre.getText();
                String desc = txtDescripcion.getText();
                LocalTime apertura = LocalTime.parse(txtHorario_aper.getText());
                LocalTime cierre = LocalTime.parse(txtHorario_cie.getText());
                Integer aforo = Integer.parseInt(txtAforo.getText());
                Boolean reserva = se_reserva.isSelected();
                Integer duracion = Integer.parseInt(duraciontxt.getText());

                try {
                    experienciaMgr.agregarExperiencia(nombre, desc, apertura, cierre, aforo, reserva, this.destino, null, duracion);
                    showAlert("Experiencia agregado", "Se agrego con exito la experiencia!");

                    close(event);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setControllerFactory(PrimerDemoApplication.getContext()::getBean);

                    root = fxmlLoader.load(vistaOperadorDestinoControlador.class.getResourceAsStream("vistaOperadorDestino.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.getIcons().add(new Image("images/logo_final.png"));
                    stage.setResizable(false);
                    vistaOperadorDestinoControlador vistaOperadorDestinoControlador = fxmlLoader.getController();
                    vistaOperadorDestinoControlador.setDestino(this.destino);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void close(javafx.event.ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
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
}
