package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Ciudad;
import com.example.primer_demo.business.entities.Departamento;
import com.example.primer_demo.persistance.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DepartamentoMgr {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Iterable<Departamento> allDepartamentos(){
        return departamentoRepository.findAll();
    }

    public Set<Ciudad> buscarCiudades(String nombre){
        return departamentoRepository.findById(nombre).get().getCiudades();
    }
}
