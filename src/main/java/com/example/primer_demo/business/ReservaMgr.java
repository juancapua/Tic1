package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.persistance.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@Service
public class ReservaMgr {

    @Autowired
    private ReservaRepository reservaRepository;

    public Iterable<Reserva> allReservasUsuario(Usuario usuario){
        return reservaRepository.findAllByUsuario(usuario);
    }

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("root")
    private String password;

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void deleteReserva(Reserva reserva) throws SQLException {
        try (Connection conn = connect()){
            Statement st = conn.createStatement();
            Random rand = new Random();
            st.execute("delete from reserva where id = " + reserva.getId());
        } catch (Exception e){
            e.printStackTrace();
        }

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
