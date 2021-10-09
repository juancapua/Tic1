package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Operador;
import org.springframework.data.repository.CrudRepository;

public interface OperadorRepository extends CrudRepository<Operador, String> {
}
