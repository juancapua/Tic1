package com.example.primer_demo.business.entities;


import javax.persistence.*;

@Entity
@Table(name = "operadores")
public class Operador extends Usuario{

    private String telefono;
    private Boolean estado;

    public Operador(String empresa, String telefono, String email, String contresena, String documento) {
        super(empresa, email, contresena, documento);
        this.telefono = telefono;
        this.estado = true;
    }

    public Operador() {

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return super.getMail();
    }

    public void setEmail(String email) {
        super.setMail(email);
    }


    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
