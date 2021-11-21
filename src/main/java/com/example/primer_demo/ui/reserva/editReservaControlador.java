package com.example.primer_demo.ui.reserva;

import com.example.primer_demo.business.ReservaMgr;
import com.example.primer_demo.business.entities.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.channels.AcceptPendingException;
import java.sql.SQLException;

@Component
public class editReservaControlador {

    private Reserva reserva;

    @Autowired
    private ReservaMgr reservaMgr;

    public void setReserva(Reserva reserva){
        this.reserva = reserva;
    }

    @FXML
    void cancelar(ActionEvent event){
        close(event);
    }

    @FXML
    void eliminarReserva(ActionEvent event) throws SQLException {
        reservaMgr.deleteReserva(this.reserva);
        showAlert("Eliminacion exitosa", "La reserva indicada se eliminino correctamente");
        close(event);
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
