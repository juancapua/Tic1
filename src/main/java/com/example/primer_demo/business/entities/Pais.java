package com.example.primer_demo.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "Paises")
public class Pais {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String nombre;


    public Pais() {
    }

    public Pais(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
