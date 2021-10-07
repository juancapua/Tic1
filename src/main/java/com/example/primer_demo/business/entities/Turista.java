package com.example.primer_demo.business.entities;

import org.springframework.boot.SpringApplication;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="Turista")
public class Turista extends Usuario {

    @ManyToMany
    private List<Etiquetas> etiquetas;

}
