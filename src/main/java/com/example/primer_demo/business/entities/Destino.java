package com.example.primer_demo.business.entities;

import javax.persistence.*;

@Entity
@Table(name="Destino")
public class Destino {

    @Id
    private int Id;
    private String nombre;
    private String contacto;
    @OneToMany(mappedBy = "id_destino")
    private Departamento departamento;

}
