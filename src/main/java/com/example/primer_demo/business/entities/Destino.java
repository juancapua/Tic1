package com.example.primer_demo.business.entities;

import javax.persistence.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Destino")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    private String contacto;
    private Integer aforo;
    private LocalTime horario_apertura;
    private LocalTime horario_cierre;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "destino_id")
    private Set<Experiencia> experiencias;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_destino")
    private Set<Entrada> entradas;
    @ManyToOne
    @JoinColumn(name = "id_operador")
    private Operador operador;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Etiqueta> etiquetas;
    @ManyToOne(targetEntity = Departamento.class)
    private Departamento departamento;
    public Destino(){

    }

    public Destino(String nombre, String contacto, List<String> images, Departamento departamento){
        this.setImages(images);
        this.setNombre(nombre);
        this.setContacto(contacto);
        this.departamento = departamento;
        this.etiquetas = new HashSet<>();
    }


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

    public Set<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(Set<Entrada> entradas) {
        this.entradas = entradas;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public LocalTime getHorario_apertura() {
        return horario_apertura;
    }

    public void setHorario_apertura(LocalTime horario_apertura) {
        this.horario_apertura = horario_apertura;
    }

    public LocalTime getHorario_cierre() {
        return horario_cierre;
    }

    public void setHorario_cierre(LocalTime horario_cierre) {
        this.horario_cierre = horario_cierre;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

}
