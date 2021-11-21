package com.example.primer_demo.business.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
public class Turista {
    private Long ci;

    public void setCi(Long ci) {
        this.ci = ci;
    }

    @Id
    public Long getCi() {
        return ci;
    }

    private Set<Reserva> reservas;


    private String Nombre;

    @Basic
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    private String apellido;

    @Basic
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    private LocalDate fechaDeNacimiento;

    @Basic
    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    private Boolean vacunado;

    @Basic
    public Boolean getVacunado() {
        return vacunado;
    }

    public void setVacunado(Boolean vacunado) {
        this.vacunado = vacunado;
    }

    private String telefono;

    @Basic
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
