package com.example.primer_demo.business.entities;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "experiencias")
public class Experiencia {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private Boolean esta_autorizada;
    private String descripcion;
    private LocalTime horario_apertura;
    private LocalTime horario_cierre;
    private Integer aforo;
    private Boolean se_reserva;
    @ManyToOne(targetEntity = Destino.class)
    private Destino destino;

    public Experiencia() {
    }

    public Experiencia(String nombre, String descripcion, LocalTime horario_apertura, LocalTime horario_cierre, Integer aforo, Boolean se_reserva, Destino destino, String tipo, int duracion) {
        this.nombre = nombre;
        this.esta_autorizada = false;
        this.descripcion = descripcion;
        this.horario_apertura = horario_apertura;
        this.horario_cierre = horario_cierre;
        this.aforo = aforo;
        this.se_reserva = se_reserva;
        this.destino = destino;
        this.tipo = tipo;
        this.duracion = duracion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEsta_autorizada() {
        return esta_autorizada;
    }

    public void setEsta_autorizada(Boolean esta_autorizada) {
        this.esta_autorizada = esta_autorizada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalTime getHorario_apertura() {
        return horario_apertura;
    }

    public void setHorario_apertura(LocalTime horario_apertura) {
        this.horario_apertura = horario_apertura;
    }

    public LocalTime getHorario_cierre() {
        return horario_cierre;
    }

    public void setHorario_cierre(LocalTime horario_cierre) {
        this.horario_cierre = horario_cierre;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public Boolean getSe_reserva() {
        return se_reserva;
    }

    public void setSe_reserva(Boolean se_reserva) {
        this.se_reserva = se_reserva;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    @Basic
    private String imagen;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Basic
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "favoritos")
    private Set<Usuario> usuarios;

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Basic
    private Integer duracion;

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "experiencia")
    private Set<Reserva> reservas;

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
