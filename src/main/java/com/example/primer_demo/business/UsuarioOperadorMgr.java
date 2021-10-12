package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.business.entities.UsuarioOperador;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioOperadorRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import com.example.primer_demo.ui.usuario.UsuarioControlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioOperadorMgr {

    @Autowired
    private UsuarioOperadorRepository usuarioOperadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OperadorRepository operadorRepository;

    public void agregarUsuarioOperador (String nombre, String mail, String contrasena, Operador operador) throws InvalidInformation, UsuarioAlreadyExist {

        if(nombre == null || "".equals(nombre) || mail == null || "".equals(mail) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(usuarioOperadorRepository.findById(nombre).isPresent() || usuarioRepository.findById(nombre).isPresent() || operadorRepository.findById(nombre).isPresent()){
            throw new UsuarioAlreadyExist("El nombre de usuario ya esta utilizado");
        }

        UsuarioOperador nuevoUsuario = new UsuarioOperador(nombre, mail, contrasena, operador);
        usuarioOperadorRepository.save(nuevoUsuario);
//        Set<UsuarioOperador> usuarios = operador.getUsuarioOperadorList();
//        usuarios.add(nuevoUsuario);
//        operador.setUsuarioOperadorList(usuarios);
//        operadorRepository.save(operador);

    }

    public boolean ingresar(String nombre, String contrasena) throws InvalidInformation, UsuarioNotExist {

        if(usuarioOperadorRepository.findByNombreDeUsuarioAndContrasena(nombre, contrasena) != null){return true;}
        return false;
    }

    public void bloaquear(UsuarioOperador usuarioOperador){
        usuarioOperador.setEstado(false);
        usuarioOperadorRepository.save(usuarioOperador);
    }

    public void habilitar(UsuarioOperador usuarioOperador){
        usuarioOperador.setEstado(true);
        usuarioOperadorRepository.save(usuarioOperador);

    }

}
