package com.example.primer_demo.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "administradores")
public class Admin extends Usuario{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    public Admin() {
    }

    public Admin(String nombreDeUsuario, String mail, String contrasena) {
        super(nombreDeUsuario, mail, contrasena);
    }

    
}
