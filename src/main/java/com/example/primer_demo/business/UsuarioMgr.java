package com.example.primer_demo.business;


import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Service
public class UsuarioMgr {

    @Autowired
    private UsuarioRepository usuarioRepository;




    public void agregarUsuario (String nombre, String mail, String contrasena, Long documento, String pais, LocalDate fechaNac, Boolean vacunado) throws InvalidInformation, UsuarioAlreadyExist {

        if(nombre == null || "".equals(nombre) || mail == null || "".equals(mail) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(usuarioRepository.findById(nombre).isPresent()){
            throw new UsuarioAlreadyExist("El nombre de usuario ya esta utilizado");
        }

        Usuario nuevoUsuario = new Usuario(nombre, mail, contrasena, documento, pais, fechaNac, vacunado);
        usuarioRepository.save(nuevoUsuario);

    }

    public boolean ingresar(String nombre, String contrasena) throws InvalidInformation, UsuarioNotExist {

        if(nombre == null || "".equals(nombre) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(!usuarioRepository.existsById(nombre)){
            throw new UsuarioNotExist("El nombre de usuario no existe");
        }
        if(usuarioRepository.findById(nombre).get().getContrasena().equals(contrasena)){
            return true;
        }
        return false;
    }




}
