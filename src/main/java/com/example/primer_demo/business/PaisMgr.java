package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Pais;
import com.example.primer_demo.persistance.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
public class PaisMgr {

    @Autowired
    private PaisRepository paisRepository;


    public Iterable<Pais> allPaises(){
        return paisRepository.findAll();
    }
}
