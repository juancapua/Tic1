package com.example.primer_demo.ui.reserva;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.ReservaMgr;
import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.ui.usuario.vistaPerfilControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import java.sql.SQLException;

@Component
public class editReservaControlador {

    private Reserva reserva;
    private Usuario usuario;
    private Parent root;

    @Autowired
    private ReservaMgr reservaMgr;

    public void setReserva(Reserva reserva, Usuario usuario){
        this.reserva = reserva;
        this.usuario = usuario;
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
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

    @FXML
    void eliminarReserva(ActionEvent event) throws SQLException, IOException {
        reservaMgr.deleteReserva(this.reserva);
        showAlert("Eliminacion exitosa", "La reserva indicada se eliminino correctamente");
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




    private void close(javafx.event.ActionEvent event) {
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
}
