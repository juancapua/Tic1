package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Pais;
import org.springframework.data.repository.CrudRepository;

public interface PaisRepository extends CrudRepository<Pais, String> {
}
