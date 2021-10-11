package com.example.primer_demo.business.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operadores")
public class Operador {

    @Id
    private String nombreDeUsuario;
    private String mail;
    private String contrasena;
    private int telefono;
    private String direccion;
    private Boolean estado;

    @OneToMany(mappedBy = "nombreDeUsuario")
    private List<UsuarioOperador> usuarioOperadorList;

    public Operador(String nombreDeUsuario, String mail, String contrasena, int telefono, String direccion) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.mail = mail;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = false;
        this.usuarioOperadorList = new ArrayList<>();
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
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

    public List<UsuarioOperador> getUsuarioOperadorList() {
        return usuarioOperadorList;
    }

    public void setUsuarioOperadorList(UsuarioOperador usuarioOperador) {
        this.usuarioOperadorList.add(usuarioOperador);
    }
}
