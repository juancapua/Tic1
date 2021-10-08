package com.example.primer_demo.business.entities;

import org.springframework.boot.SpringApplication;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Turista")
@PrimaryKeyJoinColumn(name="mail")
public class Turista extends Usuario {

    @ManyToMany
    private List<Etiqueta> etiquetas;


    public Turista(String usuario, String mail, String contrasena, List<Etiqueta> etiquetas){
        super(usuario, mail,contrasena);
        this.etiquetas=etiquetas;
    }

}
