package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Departamento;
import com.example.primer_demo.persistance.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartamentoMgr {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Iterable<Departamento> allDepartamentos(){
        return departamentoRepository.findAll();
    }

    public Departamento traerDepartamento(String nombre){
        Iterable<Departamento> departamentos = departamentoRepository.findAll();
        for(Departamento x: departamentos){
            if(x.getNombre_pk().equals(nombre)){
                return x;
            }
        }
        return null;
    }

}
