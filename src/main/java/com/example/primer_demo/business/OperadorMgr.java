package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperadorMgr {

    @Autowired
    private OperadorRepository operadorRepository;



    public void agregarOperador (String nombreDeUsuario, String mail, String contrasena, Long telefono, String direccion) throws InvalidInformation, UsuarioAlreadyExist {

        if(nombreDeUsuario == null || "".equals(nombreDeUsuario) || mail == null || "".equals(mail) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(operadorRepository.findById(nombreDeUsuario).isPresent()){
            throw new UsuarioAlreadyExist("El nombre de usuario ya esta utilizado");
        }

        Operador nuevoOperador = new Operador(nombreDeUsuario, mail, contrasena, telefono, direccion);
        operadorRepository.save(nuevoOperador);


    }



    public boolean ingresar(String nombre, String contrasena) throws InvalidInformation, UsuarioNotExist {

        if(operadorRepository.findByNombreDeUsuarioAndContrasena(nombre, contrasena) != null){return true;}
        return false;
    }
}
