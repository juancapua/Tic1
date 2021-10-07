package com.example.primer_demo.business.entities;

import javafx.scene.control.Label;
import net.bytebuddy.agent.builder.AgentBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "etiquetas")
public class Etiquetas {

    @Id
    private String nombre;

    @ManyToMany
    private List<Turista> turistaList;

    public Etiquetas() {
    }

    public Etiquetas(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
