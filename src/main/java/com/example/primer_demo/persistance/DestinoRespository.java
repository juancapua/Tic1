package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Departamento;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Operador;
import org.springframework.data.repository.CrudRepository;

public interface DestinoRespository extends CrudRepository<Destino, Integer> {
    Destino findByNombre(String nombre);

    Iterable<Destino> findAllByNombreContaining(String filtro);

    Iterable<Destino> findAllByOperador(Operador operador);

    Boolean existsByNombre(String nombre);

    Iterable<Destino> findAllByDepartamento(Departamento departamento);

    Iterable<Destino> findAllByDepartamentoAndNombreContaining(Departamento departamento, String texto);

}
