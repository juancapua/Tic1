package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Etiqueta;
import org.springframework.data.repository.CrudRepository;

public interface EtiquetasRepository extends CrudRepository<Etiqueta, String> {


}
