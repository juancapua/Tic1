package com.example.primer_demo.business.entities;


import javax.persistence.*;

@Entity
@Table(name = "operadores")
public class Operador {

    @Id
    private String nombreDeUsuario;
    private String mail;
    private String contrasena;
    private Long telefono;
    private String direccion;
    private Boolean estado;

    public Operador(String nombreDeUsuario, String mail, String contrasena, Long telefono, String direccion) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.mail = mail;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = false;
    }

    public Operador() {

    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
