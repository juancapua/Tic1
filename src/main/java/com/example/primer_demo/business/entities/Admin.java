package com.example.primer_demo.business.entities;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="Admin")
public class Admin extends Usuario{

    public Admin() {
    }

    public Admin(String usuario, String mail, String contrasena,String documento){
        super(usuario, mail, contrasena, documento);
    }
}
