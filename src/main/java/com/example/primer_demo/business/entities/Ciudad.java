package com.example.primer_demo.business.entities;


import javax.persistence.*;

@Entity
@Table(name="Ciudad")
public class Ciudad {

    @Id
    private String nombre_pk;

    @ManyToOne()
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    public String getNombre_pk() {
        return nombre_pk;
    }

    public void setNombre_pk(String nombre_pk) {
        this.nombre_pk = nombre_pk;
    }
}
