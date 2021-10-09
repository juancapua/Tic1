package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, String> {

    Admin findAdminByNombreUsuarioAndContrasena(String usuario, String contrasena);
}
