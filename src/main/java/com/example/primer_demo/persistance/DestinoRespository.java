package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Destino;
import org.springframework.data.repository.CrudRepository;

public interface DestinoRespository extends CrudRepository<Destino, Integer> {
    Destino findByNombre(String nombre);

}
