package com.example.primer_demo.business.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@IdClass(ReservaPK.class)
public class Reserva implements Serializable {

    public Reserva(){

    }

    public Reserva(LocalDate fecha, Experiencia experiencia, Usuario usuario, LocalTime hora, Set<Turista> turistas){
        this.setFecha(fecha);
        this.experiencia = experiencia;
        this.usuario = usuario;
        this.turistas = turistas;
        if (experiencia.getTipo().equals("TED")){
            this.hora = LocalTime.MIDNIGHT;
        } else {
            this.hora = hora;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    private LocalDate fecha;


    @ManyToOne(optional = false)
    @Id
    private Experiencia experiencia;

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }


    @ManyToOne(optional = false)
    @Id
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Turista> turistas;

    public void setTuristas(Set<Turista> turistas) {
        this.turistas = turistas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
