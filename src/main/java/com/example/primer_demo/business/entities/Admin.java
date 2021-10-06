package com.example.primer_demo.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "administradores")
public class Admin extends Usuario{

    public Admin() {
    }

    public Admin(String nombreDeUsuario, String mail, String contrasena) {
        super(nombreDeUsuario, mail, contrasena);
    }

    
}
