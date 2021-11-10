package com.example.primer_demo.business.entities;

import javafx.scene.control.Label;
import net.bytebuddy.agent.builder.AgentBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "etiquetas")
public class Etiqueta {

    @Id
    private String nombre;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "intereses_usuarios",
            joinColumns = {@JoinColumn(name = "etiqueta")},
            inverseJoinColumns = {@JoinColumn(name = "username")}
    )
    private Set<Usuario> usuarios;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE

    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "etiquetas_destinos",
            joinColumns = {@JoinColumn(name = "etiqueta")},
            inverseJoinColumns = {@JoinColumn(name = "id_destino")}
    )
    private Set<Destino> destinos;

    public Etiqueta() {
    }

    public Etiqueta(String nombre) {
        this.nombre = nombre;
        this.usuarios = new HashSet<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void addUsuario(Usuario usuario){
        this.usuarios.add(usuario);
    }

    public Set<Destino> getDestinos() {
        return destinos;
    }

    public void setDestinos(Set<Destino> destinos) {
        this.destinos = destinos;
    }

    public void addDestino(Destino destino){
        this.destinos.add(destino);

    }
}
