package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Dia;
import com.example.primer_demo.persistance.DiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaMgr {
    @Autowired
    private DiaRepository diaRepository;

    public Iterable<Dia> allDias(){
        return diaRepository.findAll();
    }
}
