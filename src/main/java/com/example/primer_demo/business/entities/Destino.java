package com.example.primer_demo.business.entities;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Destino")
public class Destino{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    private String contacto;
    private Integer aforo;
    private LocalTime horario_apertura;
    private LocalTime horario_cierre;
    private Boolean habilitada;
    private String descripcion;
    private String direccion;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<byte[]> images;

    //mappedBy es el atributo tipo Destino de la clase especificada en la lista
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "destino")
    private Set<Experiencia> experiencias;
    @OneToMany( fetch = FetchType.EAGER, mappedBy = "destino")
    private Set<Entrada> entradas;

    //JoinColumn es la columna que referenciará a la entidad target
    @ManyToOne
    @JoinColumn(name = "id_operador")
    private Operador operador;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "destinos")
    private Set<Etiqueta> etiquetas;

    @ManyToOne(targetEntity = Departamento.class)
    private Departamento departamento;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Dia> dias;


    public Destino(){

    }

    public Destino(String nombre, String contacto, Integer aforo, LocalTime horario_apertura, LocalTime horario_cierre, String direccion, Departamento departamento, Operador operador, String descripcion, byte[] imagen, Set<Dia> dias) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.aforo = aforo;
        this.horario_apertura = horario_apertura;
        this.horario_cierre = horario_cierre;
        this.direccion = direccion;
        this.departamento = departamento;
        this.operador = operador;
        this.etiquetas = new HashSet<>();
        this.habilitada = false;
        this.descripcion = descripcion;
        this.images = new ArrayList<>();
        this.images.add(imagen);
        this.dias = dias;
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

    public List<byte[]> getImages() {
        return images;
    }

    public void addImages(byte[] imagen) {
        this.images.add(imagen);
    }

    public Set<Experiencia> getExperiencias() {
        return experiencias;
    }

    public void addExperiencias(Experiencia experiencias) {
        this.experiencias.add(experiencias);
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

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<Dia> getDias() {
        return dias;
    }

    public void setDias(Set<Dia> dias) {
        this.dias = dias;
    }

    public void addEtiquetas(Set<Etiqueta> etiquetas){
        for(Etiqueta x: etiquetas){
            this.etiquetas.add(x);
        }
    }


    public int getId() {
        return id;
    }
}
