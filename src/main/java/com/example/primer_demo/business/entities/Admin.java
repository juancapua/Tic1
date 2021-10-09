package com.example.primer_demo.business.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Admin")
public class Admin{


    @Id
    private String nombreUsuario;
    private String mail;
    private String contrasena;

    public Admin() {
    }

    public Admin(String nombreUsuario, String mail, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.mail = mail;
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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
}
