package com.example.primer_demo.business.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

    //LA ID TIENE QUE SER EL MAIL
    @Id
    @Column(name = "mail", unique = true, nullable = false)
    private String mail;
    private String nombreDeUsuario;
    private String contrasena;



    public Usuario(){

    }

    public Usuario(String nombreDeUsuario, String mail, String contrasena) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.mail = mail;
        this.contrasena = contrasena;
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
}
