package com.example.primer_demo.business.entities;

import org.springframework.boot.SpringApplication;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Turista")
public class Turista extends Usuario {

}
