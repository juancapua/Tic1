package com.example.primer_demo.business.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "etiquetas")
public class Etiquetas {

    @Id
    private String nombre;

    public Etiquetas() {
    }

    public Etiquetas(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
