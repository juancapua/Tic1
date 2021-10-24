package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.persistance.EtiquetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaMgr {

    @Autowired
    private EtiquetasRepository etiquetasRepository;


    public Iterable<Etiqueta> listaEtiquetas(){
        return etiquetasRepository.findAll();
    }

    public void agregarEtiqueta(String nombre) throws UsuarioAlreadyExist {

        if(etiquetasRepository.findById(nombre).isPresent()){
            throw new UsuarioAlreadyExist("La etiqueta ya esta registrada en el siatema");
        }

        Etiqueta nuevaEtiqueta = new Etiqueta(nombre);
        etiquetasRepository.save(nuevaEtiqueta);

    }

}
