package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Ciudad;
import com.example.primer_demo.business.entities.Departamento;
import com.example.primer_demo.persistance.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadMgr {

    @Autowired
    private CiudadRepository ciudadRepository;

    public Iterable<Ciudad> ciudadesDeDepartamento(Departamento departamento){
        return ciudadRepository.findAllByDepartamento(departamento);
    }
}
