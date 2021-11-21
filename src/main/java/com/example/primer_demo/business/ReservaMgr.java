package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.persistance.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaMgr {

    @Autowired
    private ReservaRepository reservaRepository;

    public void deleteReserva(Reserva reserva){
        reservaRepository.delete(reserva);
    }
}
