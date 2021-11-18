package com.example.primer_demo.business.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaPK implements Serializable {

    public ReservaPK(){

    }

    public ReservaPK(LocalDate fecha, Experiencia experiencia, Usuario usuario, LocalTime hora){
        this.fecha = fecha;
        this.experiencia = experiencia;
        this.usuario = usuario;
        this.hora = hora;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    private LocalDate fecha;

    @Id
    @OneToOne
    private Experiencia experiencia;

    @Id
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Usuario getManyToOne() {
        return usuario;
    }

    public void setManyToOne(Usuario usuario) {
        this.usuario = usuario;
    }

    @Id
    private LocalTime hora;

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
