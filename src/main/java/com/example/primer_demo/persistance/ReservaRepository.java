package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Reserva;
import com.example.primer_demo.business.entities.UsuarioOperador;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Integer> {

}
