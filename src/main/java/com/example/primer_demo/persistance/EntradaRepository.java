package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Entrada;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepository extends CrudRepository<Entrada, Integer> {
    public Entrada findEntradaByTituloAndDestino_Id(String titulo, Integer destino);
}
