package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Dia;
import org.springframework.data.repository.CrudRepository;

public interface DiaRepository extends CrudRepository<Dia, String> {
}
