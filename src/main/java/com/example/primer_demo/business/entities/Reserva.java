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

    public Reserva(LocalDate fecha, Experiencia experiencia, Usuario usuario, LocalTime hora, int personas){
        this.setFecha(fecha);
        this.experiencia = experiencia;
        this.usuario = usuario;
        if (experiencia.getTipo().equals("PED")){
            this.hora = LocalTime.MIDNIGHT;
        } else {
            this.hora = hora;
        }
        this.setPersonas(personas);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    private LocalDate fecha;


    @ManyToOne(optional = false)
    @Id
    @JoinColumn(name = "id_experiencia")
    private Experiencia experiencia;

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }


    @ManyToOne(optional = false)
    @Id
    @JoinColumn(name = "usuario_nombre_de_usuario")
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

    @Basic
    private Integer Personas;

    public Integer getPersonas() {
        return Personas;
    }

    public void setPersonas(Integer personas) {
        Personas = personas;
    }


}
