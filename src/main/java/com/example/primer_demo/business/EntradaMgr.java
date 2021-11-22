package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Entrada;
import com.example.primer_demo.persistance.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

@Service
public class EntradaMgr {

    @Autowired
    private EntradaRepository entradaRepository;


    public Entrada getEntradaPorNombreYDestino(String nombre, Integer destino){
        return entradaRepository.findEntradaByTituloAndDestino_Id(nombre,destino);
    }

    public void guardarEntrada(String titulo, String texto, Destino destino){
        Entrada entrada = new Entrada(titulo,texto,destino);
        entradaRepository.save(entrada);
    }

    public void cambiarTitulo(Entrada entrada, String nuevoTitulo){
        entrada.setTitulo(nuevoTitulo);
        entradaRepository.save(entrada);
    }
}
