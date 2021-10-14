package com.example.primer_demo.business.entities;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="Destino")
public class Destino {

    @Id
    private int id;
    private String nombre;
    private String contacto;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @OneToMany(fetch = FetchType.EAGER,targetEntity = Experiencia.class)
    private Set<Experiencia> experiencias;

    public Destino(){

    }

    public Destino(String nombre, String contacto, List<String> images, Departamento departamento){
        this.setImages(images);
        this.setNombre(nombre);
        this.setContacto(contacto);
        this.departamento = departamento;
    }

    @ManyToOne(targetEntity = Departamento.class)
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Set<Experiencia> getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(Set<Experiencia> experiencias) {
        this.experiencias = experiencias;
    }
}
