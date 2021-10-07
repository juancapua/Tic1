package com.example.primer_demo.business.entities;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Destino")
public class Destino {

    @Id
    private int Id;

    private String nombre;
    private String contacto;
    private Departamento departamento;

}
