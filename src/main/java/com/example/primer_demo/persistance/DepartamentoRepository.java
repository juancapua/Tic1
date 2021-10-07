package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Departamento;
import org.springframework.data.repository.CrudRepository;

public interface DepartamentoRepository extends CrudRepository<Departamento, String> {

}
