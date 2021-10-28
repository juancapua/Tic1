package com.example.primer_demo.business.entities;

import javax.persistence.*;

@Entity
@Table(name="Entrada")
public class Entrada {

    @Id
    @GeneratedValue
    private String id;

    private String titulo;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "destino")
    private Destino destino;

    public Entrada(){

    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
