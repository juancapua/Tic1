package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Turista;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;

@Service
public class OperadorMgr {

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;




    public void agregarUsuarioOperador (String nombre, String mail, String contrasena, String documento) throws InvalidInformation, UsuarioAlreadyExist {

        if(nombre == null || "".equals(nombre) || mail == null || "".equals(mail) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(usuarioRepository.findById(nombre).isPresent()){
            throw new UsuarioAlreadyExist("El nombre de usuario ya esta utilizado");
        }

        Usuario nuevoUsuario = new Usuario(nombre, mail, contrasena, documento);
        usuarioRepository.save(nuevoUsuario);

    }



    public boolean ingresar(String nombre, String contrasena) throws InvalidInformation, UsuarioNotExist {

        if(nombre == null || "".equals(nombre) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(!operadorRepository.existsById(nombre)){
            throw new UsuarioNotExist("El nombre de usuario no existe");
        }
        if(operadorRepository.findById(nombre).get().getContrasena().equals(contrasena)){
            return true;
        }
        return false;
    }
}
