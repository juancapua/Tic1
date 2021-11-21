package com.example.primer_demo.ui.experiencia;

import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.persistance.ReservaRepository;
import com.example.primer_demo.ui.Inicio.InicioControlador;
import com.jfoenix.controls.JFXTabPane;
import eu.iamgio.animated.AnimatedHBox;
import eu.iamgio.animated.AnimationPair;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.tomcat.jni.Local;
import org.controlsfx.control.spreadsheet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class HacerReservaControlador {

    @FXML
    public Text reservaConfirmacion;

    @Autowired
    private ReservaRepository reservaRepository;

    private Experiencia experiencia;

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


    private int lugaresDisponibles = 0;

    private SpreadsheetView horarios;

    @FXML
    private AnchorPane reservaContainer;

    @FXML
    private Button check;

    @FXML
    private MFXDatePicker datePicker;

    @FXML
    private Spinner<Integer> spinner;

    private AnimatedHBox animatedHBox;

    private Text textoDepartamento;
    private Text textoDestino;
    private Text textoExperiencia;

    @FXML
    private Text detalles;

    private LocalDate fechaReserva;
    private LocalTime horaReserva;

    private Stage primaryStage;

    public HacerReservaControlador() {
    }

    public void init(Experiencia experiencia, Stage primaryStage) throws InterruptedException {
        this.primaryStage = primaryStage;
        this.experiencia = experiencia;
        detalles.setText(experiencia.getDescripcion());
        animatedHBox = new AnimatedHBox(AnimationPair.fade());
        titulo.getChildren().add(animatedHBox);
        animatedHBox.setVisible(true);
        animatedHBox.setPadding(new Insets(12));
        textoDepartamento = newText(experiencia.getDestino().getDepartamento().getNombre_pk() + ", " + experiencia.getDestino().getNombre() + ", " + experiencia.getNombre(), 24);
        animatedHBox.getChildren().add(textoDepartamento);
        try {
            InputStream x = new ByteArrayInputStream(experiencia.getImagen());
            imagen.setBackground(new Background(new BackgroundImage(new Image(x), null, null, BackgroundPosition.CENTER, null)));
        } catch (Exception e) {
        }
        tab2.setDisable(true);

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (experiencia.getTipo() == null) {
                    try {
                        int selectedValue = (Integer) horarios.getGrid().getRows().get(horarios.getSelectionModel().getSelectedCells().get(0).getRow()).get(horarios.getSelectionModel().getSelectedCells().get(0).getColumn()).getItem();
                        siguiente.setDisable(horarios.getSelectionModel().getSelectedCells().size() == 0 || selectedValue < newValue);
                        tab2.setDisable(horarios.getSelectionModel().getSelectedCells().size() == 0 || selectedValue < newValue);
                    } catch (Exception exception) {
                    }
                } else {
                    siguiente.setDisable((lugaresDisponibles <= 0 || lugaresDisponibles < spinner.getValue()));
                    tab2.setDisable((lugaresDisponibles <= 0 || lugaresDisponibles < spinner.getValue()));
                }
            }
        });
        spinner.setVisible(true);

        if (experiencia.getTipo() == null) {
            loadReservaHoras();
            datePicker.setVisible(false);
            check.setVisible(false);
        } else {
            horarios.setVisible(false);
        }

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue.getId().equals("tab2")) {
                    if (experiencia.getTipo() != null && !experiencia.getTipo().equals("PED")) {
                        reservaConfirmacion.setText("Se reserva el día " + fechaReserva + " a las " + horaReserva + " en la actividad \"" + experiencia.getNombre() + "\" y esta tiene un duración de " + experiencia.getDuracion() + " horas.");
                    } else {
                        reservaConfirmacion.setText("Se reserva el día " + fechaReserva + " para " + spinner.getValue() + " personas.");
                    }
                }
            }
        });

    }

    private Text newText(String texto, int size) {
        Text text = new Text();
        text.setText(texto);
        text.setVisible(true);
        text.fontProperty().set(Font.font("Segoe UI", size));
        return text;
    }


    public void siguiente() {
        tabPane.getSelectionModel().select(1);
        if (experiencia.getTipo() != null && !experiencia.getTipo().equals("PED")) {
            reservaConfirmacion.setText("Se reserva el día " + fechaReserva + " a las " + horaReserva + " en la actividad \"" + experiencia.getNombre() + "\" y esta tiene un duración de " + experiencia.getDuracion() + " horas.");
        } else {
            reservaConfirmacion.setText("Se reserva el día " + fechaReserva + " en la actividad \"" + experiencia.getNombre() + "\" para " + spinner.getValue() + " personas.");
        }
    }

    public void reservar(Usuario usuario, Experiencia experiencia, LocalTime hora, LocalDate fecha, Integer personas) {
        try{
            insertarReserva(usuario.getNombreDeUsuario(),experiencia.getId(),horaReserva,fechaReserva,personas);
        } catch (Exception e){

        }
    }

    private void loadReservaHoras() {
        int intervalo = (experiencia.getHorario_cierre().getHour() - experiencia.getHorario_apertura().getHour()) / experiencia.getDuracion();
        if (intervalo == 0) {
            intervalo = 24 / experiencia.getDuracion();
        }
        Grid grilla = new GridBase(intervalo,
                7);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        Iterable<Reserva> reservas = reservaRepository.findAll();
        for (int row = 0; row < grilla.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grilla.getColumnCount(); ++column) {
                int aforoHora;
                int count = 0;
                LocalDate now = LocalDate.now();
                for (Reserva res : reservas) {
                    boolean b = res.getExperiencia().getId() == experiencia.getId() &&
                            res.getFecha().getDayOfMonth() == now.plusDays(column + 7 - now.getDayOfWeek().getValue()).getDayOfMonth() &&
                            res.getHora().getHour() == row * experiencia.getDuracion() + experiencia.getHorario_apertura().getHour();
                    if (b) {
                        count += res.getPersonas();
                    }
                }
                aforoHora = experiencia.getAforo() - count;
                list.add(SpreadsheetCellType.INTEGER.createCell(row, column, 1, 1, aforoHora));
            }
            rows.add(list);
        }
        grilla.setRows(rows);
        for (int row = 0; row < grilla.getRowCount(); ++row) {
            grilla.getRowHeaders().add(Integer.toString(experiencia.getHorario_apertura().getHour() + row * experiencia.getDuracion()));
        }
        LocalDate now = LocalDate.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        LocalDate domingo = now.plusDays(7 - dayOfWeek);
        LocalDate lunes = now.plusDays(8 - dayOfWeek);
        LocalDate martes = now.plusDays(9 - dayOfWeek);
        LocalDate miercoles = now.plusDays(10 - dayOfWeek);
        LocalDate jueves = now.plusDays(11 - dayOfWeek);
        LocalDate viernes = now.plusDays(12 - dayOfWeek);
        LocalDate sabado = now.plusDays(13 - dayOfWeek);
        grilla.getColumnHeaders().setAll("Dom " + domingo.format(DateTimeFormatter.ofPattern("dd/MM")),
                "Lun " + lunes.format(DateTimeFormatter.ofPattern("dd/MM")),
                "Mar " + martes.format(DateTimeFormatter.ofPattern("dd/MM")),
                "Mie " + miercoles.format(DateTimeFormatter.ofPattern("dd/MM")),
                "Jue " + jueves.format(DateTimeFormatter.ofPattern("dd/MM")),
                "Vie " + viernes.format(DateTimeFormatter.ofPattern("dd/MM")),
                "Sab " + sabado.format(DateTimeFormatter.ofPattern("dd/MM")));
        horarios = new SpreadsheetView(grilla);
        System.out.println(horarios.getOnMouseMoved());
        System.out.println(horarios.getOnMouseClicked());
        horarios.setEditable(false);
        horarios.setOnMousePressed(null);
        horarios.setOnMouseClicked(null);
        horarios.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        horarios.setMaxWidth(7 * horarios.getColumns().get(0).getWidth());
        horarios.setPrefHeight(16 * intervalo + 100);
        horarios.setMaxHeight(16 * 10);
        horarios.setLayoutY(54);
        reservaContainer.getChildren().add(horarios);
        horarios.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            int selectedValue = (Integer) horarios.getGrid().getRows().get(horarios.getSelectionModel().getSelectedCells().get(0).getRow()).get(horarios.getSelectionModel().getSelectedCells().get(0).getColumn()).getItem();
            if (spinner.getValue() <= selectedValue && horarios.getSelectionModel().getSelectedCells().size() != 0 && !horarios.getSelectionModel().getSelectedCells().get(0).toString().equals("0")) {
                siguiente.setDisable(false);
                tab2.setDisable(false);
                fechaReserva = now.plusDays(7 + horarios.getSelectionModel().getSelectedCells().get(0).getColumn() - dayOfWeek);
                horaReserva = LocalTime.of(horarios.getSelectionModel().getSelectedCells().get(0).getRow() * experiencia.getDuracion() + experiencia.getHorario_apertura().getHour(), 0);

            } else if (horarios.getSelectionModel().getSelectedCells().size() == 0) {
                siguiente.setDisable(true);
                tab2.setDisable(true);
            }
        });
    }

    public void check(ActionEvent actionEvent) {
        Iterable<Reserva> reservas = reservaRepository.findAll();
        int count = 0;
        for (Reserva res : reservas) {
            Boolean b = Objects.equals(res.getExperiencia().getId(), experiencia.getId()) &&
                    res.getFecha().equals(datePicker.getDate());
            if (b) {
                count += res.getPersonas();
            }
        }
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox vBox = new VBox(20);
        lugaresDisponibles = experiencia.getAforo() - count;
        Text mensaje = new Text("Quedan " + lugaresDisponibles + " lugares disponibles para esta actividad en esa fecha");
        mensaje.setWrappingWidth(250);
        vBox.getChildren().add(mensaje);
        Scene escena = new Scene(vBox, 300, 200);
        dialog.setScene(escena);
        dialog.show();
        if (lugaresDisponibles - spinner.getValue() > 0) {
            siguiente.setDisable(false);
            fechaReserva = datePicker.getDate();
            tab2.setDisable(false);
        }
    }

    @FXML
    public void confirmar(ActionEvent actionEvent) {
        reservar(InicioControlador.usuario, experiencia, horaReserva, fechaReserva, spinner.getValue());
        primaryStage.close();
    }
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    private final String url = "jdbc:mysql://localhost:3306/demo_tic";
    private final String user = "root";
    private final String password = "root";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public void insertarReserva(String usuario, Integer experiencia, LocalTime hora, LocalDate fecha, int personas) throws SQLException {
        if(hora==null){
            hora=LocalTime.MIDNIGHT;
        }
        try (Connection conn = connect()){
            Statement st = conn.createStatement();
            Random rand = new Random();
            st.execute("INSERT INTO RESERVA VALUES(" + rand.nextInt(1000000) + ",'" + hora + "','" + fecha + "','" + usuario + "'," + experiencia + "," + personas + ")");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
