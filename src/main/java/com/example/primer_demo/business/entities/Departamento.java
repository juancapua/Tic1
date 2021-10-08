package com.example.primer_demo.business.entities;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="Departamento")
public class Departamento {
    @Id
    private String nombre_pk;
    @OneToMany(targetEntity = Ciudad.class)
    private List<Ciudad> ciudades;

    public String getNombre_pk() {
        return nombre_pk;
    }

    public void setNombre_pk(String nombre_pk) {
        this.nombre_pk = nombre_pk;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }



}
