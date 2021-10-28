package com.example.primer_demo.business;


import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.EtiquetasRepository;
import com.example.primer_demo.persistance.OperadorRepository;
import com.example.primer_demo.persistance.UsuarioOperadorRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Service
public class UsuarioMgr {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private UsuarioOperadorRepository usuarioOperadorRepository;

    @Autowired
    private EtiquetasRepository etiquetasRepository;


    public void agregarUsuario(String nombre, String mail, String contrasena, Long documento, String pais, LocalDate fechaNac, Boolean vacunado, Set<Etiqueta> etiquetas) throws InvalidInformation, UsuarioAlreadyExist {

        if (nombre == null || "".equals(nombre) || mail == null || "".equals(mail) || contrasena == null || "".equals(contrasena)) {
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if (usuarioOperadorRepository.findById(nombre).isPresent() || usuarioRepository.findById(nombre).isPresent() || operadorRepository.findById(nombre).isPresent()) {
            throw new UsuarioAlreadyExist("El nombre de usuario ya esta utilizado");
        }

        Usuario nuevoUsuario = new Usuario(nombre, mail, contrasena, documento, pais, fechaNac, vacunado);
        nuevoUsuario.addEtiquetas(etiquetas);
        usuarioRepository.save(nuevoUsuario);
        for (Etiqueta x : etiquetas) {
            x.addUsuario(nuevoUsuario);
            etiquetasRepository.save(x);
        }

    }

    public boolean ingresar(String nombre, String contrasena) throws InvalidInformation, UsuarioNotExist {

        if (nombre == null || "".equals(nombre) || contrasena == null || "".equals(contrasena)) {
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if (!usuarioRepository.existsById(nombre)) {
            throw new UsuarioNotExist("El nombre de usuario no existe");
        }
        if (usuarioRepository.findById(nombre).get().getContrasena().equals(contrasena)) {
            return true;
        }
        return false;
    }
    
}
