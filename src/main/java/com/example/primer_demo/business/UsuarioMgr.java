package com.example.primer_demo.business;


import com.example.primer_demo.business.entities.Turista;
import com.example.primer_demo.business.entities.Usuario;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioAlreadyExist;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.TuristaRepository;
import com.example.primer_demo.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

//@Controller
//@RequestMapping
@Service
public class UsuarioMgr {

    @Autowired

    private UsuarioRepository usuarioRepository;

    @Autowired
    private TuristaRepository turistaRepository;

//    @PostMapping
//    public @ResponseBody String agregarUsuario(@RequestParam String nombre, @RequestParam String mail, @RequestParam String contrasena){
//
//        System.out.println("llegueeeee");
//        boolean existe = false;
//        for(Usuario x: usuarioRepository.findAll()){
//            if(x.getMail().equals(mail)){
//                existe = true;
//            }
//        }
//        if(!existe) {
//            Usuario nuevoUsuario = new Usuario(nombre, mail, contrasena);
//            usuarioRepository.save(nuevoUsuario);
//            return "Guardado con exito";
//        }
//        return "ERROR";
//
//
//    }


    public void agregarUsuario (String nombre, String mail, String contrasena, String documento) throws InvalidInformation, UsuarioAlreadyExist {

        if(nombre == null || "".equals(nombre) || mail == null || "".equals(mail) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(usuarioRepository.findById(nombre).isPresent()){
            throw new UsuarioAlreadyExist("El nombre de usuario ya esta utilizado");
        }

        Usuario nuevoUsuario = new Usuario(nombre, mail, contrasena, documento);
        usuarioRepository.save(nuevoUsuario);
        turistaRepository.save(new Turista(nombre,mail,contrasena, documento));

    }

    public boolean ingresar(String nombre, String contrasena) throws InvalidInformation, UsuarioNotExist {

        if(nombre == null || "".equals(nombre) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(!usuarioRepository.existsById(nombre)){
            throw new UsuarioNotExist("El nombre de usuario ya esta utilizado");
        }
        if(usuarioRepository.findById(nombre).get().getContrasena().equals(contrasena)){
            return true;
        }
        return false;
    }

    public @ResponseBody Boolean existeUsuario(@RequestParam String mail){
        boolean existe = false;
        for(Usuario x: usuarioRepository.findAll()){
            if(x.getMail().equals(mail)){
                existe = true;
            }
        }
        return existe;
    }

    @GetMapping
    public @ResponseBody Iterable<Usuario> getAllUsuarios(){

        return usuarioRepository.findAll();

    }


}
