package com.example.primer_demo.business.entities;


import javax.persistence.*;

@Entity
@Table(name = "operadores")
public class Operador {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String empresa;
    private String telefono;
    private String email;

    public Operador() {
    }

    public Operador(String empresa, String telefono, String email) {
        this.empresa = empresa;
        this.telefono = telefono;
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
