package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.entities.UsuarioOperador;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioOperadorRepository extends CrudRepository<UsuarioOperador, String> {

    Operador findByNombreDeUsuarioAndContrasena(String nombre, String contrasena);

}
