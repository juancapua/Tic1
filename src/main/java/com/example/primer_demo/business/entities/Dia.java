package com.example.primer_demo.business.entities;

import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Dias")
public class Dia {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Destino> destinos;

    public Dia() {
    }

    public Dia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addDestino(Destino destino){
        this.destinos.add(destino);
    }

    public void deleteDestino(Destino destino){
        this.destinos.remove(destino);
    }
}
