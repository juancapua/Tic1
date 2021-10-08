package com.example.primer_demo.business.entities;


import javax.persistence.*;

@Entity
@Table(name = "operadores")
public class Operador extends Usuario{

    private String telefono;

    public Operador(String empresa, String telefono, String email, String contresena, String documento) {
        super(empresa, email, contresena, documento);
        this.telefono = telefono;
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
}
