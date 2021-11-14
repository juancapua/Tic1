package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Experiencia;
import org.springframework.data.repository.CrudRepository;

public interface ExperienciaRepository extends CrudRepository<Experiencia, String> {

    Experiencia findByNombre(String nombre);
}
