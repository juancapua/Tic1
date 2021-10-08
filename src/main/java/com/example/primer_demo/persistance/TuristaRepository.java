package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Turista;
import org.springframework.data.repository.CrudRepository;

public interface TuristaRepository extends CrudRepository<Turista, String> {
}
