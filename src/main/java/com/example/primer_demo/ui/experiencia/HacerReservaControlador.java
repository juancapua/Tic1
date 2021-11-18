package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.Turista;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.persistance.ReservaRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTimePicker;
import eu.iamgio.animated.AnimatedHBox;
import eu.iamgio.animated.AnimationPair;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.spreadsheet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.TableView;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Set;

@Component
public class HacerReservaControlador {

    @Autowired
    private ReservaRepository reservaRepository;

    @FXML
    private HBox tituloExperiencia;

    @FXML
    private AnchorPane titulo;

    @FXML
    private AnchorPane imagen;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private MFXButton siguiente;

    @FXML
    private Tab tab1;
    @FXML
    private Tab tab2;
    @FXML
    private Tab tab3;
    @FXML
    private Tab tab4;

    private SpreadsheetView horarios;

    @FXML
    private AnchorPane reservaContainer;

    private AnimatedHBox animatedHBox;

    private Text textoDepartamento;
    private Text textoDestino;
    private Text textoExperiencia;

    public void init(Experiencia experiencia) throws InterruptedException {
        animatedHBox = new AnimatedHBox(AnimationPair.fade());
        titulo.getChildren().add(animatedHBox);
        animatedHBox.setVisible(true);
        animatedHBox.setPadding(new Insets(12));
        textoDepartamento = newText(experiencia.getDestino().getDepartamento().getNombre_pk() + ", " + experiencia.getDestino().getNombre() + ", " + experiencia.getNombre(), 24);
        animatedHBox.getChildren().add(textoDepartamento);
        try {
            imagen.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream(experiencia.getImagen())), null, null, BackgroundPosition.CENTER, null)));
        } catch (Exception e) {
        }
        tab2.setDisable(true);
        tab3.setDisable(true);
        tab4.setDisable(true);

        Grid grilla = new GridBase((experiencia.getHorario_cierre().getHour() - experiencia.getHorario_apertura().getHour()) / 1,
                7);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grilla.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grilla.getColumnCount(); ++column) {
                int aforoHora;
                int count = 0;
                LocalDate now = LocalDate.now();
                Iterable<Reserva> reservas = reservaRepository.findAll();
                for (Reserva res: reservas) {
                    boolean b = res.getExperiencia().getId() == experiencia.getId() &&
                            res.getFecha().isBefore(now.plusDays(7 - now.getDayOfWeek().getValue())) &&
                            res.getFecha().isAfter(now.plusDays(7 - now.getDayOfWeek().getValue()));
                    if(b){
                        count++;
                    }
                }
                aforoHora = experiencia.getAforo()-count;
                list.add(SpreadsheetCellType.INTEGER.createCell(row, column, 1, 1,aforoHora ));
            }
            rows.add(list);
        }
        horarios = new SpreadsheetView(grilla);
        reservaContainer.getChildren().add(horarios);



    }

    private Text newText(String texto, int size){
        Text text = new Text();
        text.setText(texto);
        text.setVisible(true);
        text.fontProperty().set(Font.font("Segoe UI" ,size));
        return text;
    }


    public void siguiente(){
        tabPane.getSelectionModel().select(1);
    }

    public void reservar(Usuario usuario, Set<Turista> turistas, Experiencia experiencia, LocalTime hora, LocalDate fecha){
        Reserva reserva = new Reserva(fecha, experiencia,usuario,hora,turistas);
        reservaRepository.save(reserva);
    }
}
