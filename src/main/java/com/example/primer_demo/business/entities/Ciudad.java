package com.example.primer_demo.business.entities;


import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Ciudad")
public class Ciudad {
    @Id
    private String nombre;


}
