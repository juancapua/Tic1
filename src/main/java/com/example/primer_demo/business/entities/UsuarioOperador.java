package com.example.primer_demo.business.entities;


import javax.persistence.*;

@Entity
@Table(name = "usuariosOperador")
public class UsuarioOperador {

    @Id
    private String nombreDeUsuario;
    private String mail;
    private String contrasena;
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_operador")
    private Operador operador;

    public UsuarioOperador(String nombreDeUsuario, String mail, String contrasena, Operador operador) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.mail = mail;
        this.contrasena = contrasena;
        this.estado = false;
        this.operador = operador;
    }

    public UsuarioOperador() {

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


    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }
}
