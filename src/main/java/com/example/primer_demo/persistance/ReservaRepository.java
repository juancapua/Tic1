package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.UsuarioOperador;
import org.springframework.data.repository.CrudRepository;

public interface ReservaRepository extends CrudRepository<Reserva, Integer> {
}
