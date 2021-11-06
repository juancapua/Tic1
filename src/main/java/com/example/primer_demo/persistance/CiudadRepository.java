package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Ciudad;
import com.example.primer_demo.business.entities.Departamento;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CiudadRepository extends CrudRepository<Ciudad, String> {

    Iterable<Ciudad> findAllByDepartamento(Optional<Departamento> departamento);
}
