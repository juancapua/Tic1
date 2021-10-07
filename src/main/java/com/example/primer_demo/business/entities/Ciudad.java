package com.example.primer_demo.business.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Ciudad")
public class Ciudad {

    @Id
    private String nombre;


}
