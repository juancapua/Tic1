package com.example.primer_demo.business.entities;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    //LA ID TIENE QUE SER EL MAIL
    @Id
    private String nombreDeUsuario;
    private String mail;
    private String contrasena;
    private Long documento;
    private String pais;
    private LocalDate fechaNac;
    private Boolean vacunado;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "usuarios")
    private Set<Etiqueta> etiquetas;

    public Usuario() {

    }


    public Usuario(String nombreDeUsuario, String mail, String contrasena, Long documento, String pais, LocalDate fechaNac, Boolean vacunado) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.mail = mail;
        this.contrasena = contrasena;
        this.documento = documento;
        this.pais = pais;
        this.fechaNac = fechaNac;
        this.vacunado = vacunado;
        this.etiquetas = new HashSet<>();

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

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Boolean getVacunado() {
        return vacunado;
    }

    public void setVacunado(Boolean vacunado) {
        this.vacunado = vacunado;
    }

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }
    public void addEtiquetas(Set<Etiqueta> etiquetas){
        for(Etiqueta x: etiquetas){
            this.etiquetas.add(x);
        }
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Experiencia> favoritos;

    public Set<Experiencia> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Set<Experiencia> favoritos) {
        this.favoritos = favoritos;
    }

    public Set<Experiencia> getManyToMany() {
        return favoritos;
    }

    public void setManyToMany(Set<Experiencia> favoritos) {
        this.favoritos = favoritos;
    }

    @OneToMany(mappedBy = "usuario")
    private Set<Reserva> reservas;

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }
}


