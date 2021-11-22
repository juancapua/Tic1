package com.example.primer_demo.business.entities;

import javax.persistence.*;

@Entity
@Table(name="Entrada")
public class Entrada {

    @Id
    @GeneratedValue(generator = "generator")
    private Integer id;

    private String titulo;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "destino")
    private Destino destino;

    public Entrada(){

    }

    public Entrada(String titulo, String texto, Destino destino){
        this.texto = texto;
        this.titulo = titulo;
        this.destino = destino;
    }

    public Integer getId() {
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
