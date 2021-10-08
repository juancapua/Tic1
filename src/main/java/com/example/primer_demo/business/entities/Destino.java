package com.example.primer_demo.business.entities;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="Destino")
public class Destino {

    @Id
    private int id;
    private String nombre;
    private String contacto;

    public Destino(){

    }

    public Destino(String nombre, String contacto, Departamento departamento){
        this.setNombre(nombre);
        this.setContacto(contacto);
        this.departamento = departamento;
    }

    @OneToMany
    private List<Experiencia> experiencias;

    @ManyToOne(targetEntity = Departamento.class)
    @OneToMany(mappedBy = "id_destino")
    private Departamento departamento;

    @OneToMany(targetEntity = Entrada.class)
    private List<Entrada> entrada;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}
