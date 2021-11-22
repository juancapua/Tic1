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

    public void deleteReserva(Reserva reserva){
        reservaRepository.delete(reserva);
    }

}
