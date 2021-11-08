package com.example.primer_demo.business.entities;

import com.example.primer_demo.business.ExperienciaMgr;
import org.hibernate.annotations.Generated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Time;

@Entity
public class Reserva {

    private Long id;

    @OneToOne
    private Usuario turista;

    @OneToOne
    private Experiencia experiencia;

    @Column(columnDefinition = "time default '00:00:00'")
    private Time horaInicio;

    @Column(columnDefinition = "time default '23:59:29'")
    private Time horaFin;



    public void setId(Long id) {
        this.id = id;
    }
    @Id
    public Long getId() {
        return id;
    }
}
