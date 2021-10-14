package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Destino;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DestinoRespository extends CrudRepository<Destino, Integer> {


}
