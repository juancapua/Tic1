package com.example.primer_demo.persistance;

import com.example.primer_demo.business.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
}
