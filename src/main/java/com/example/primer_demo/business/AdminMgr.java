package com.example.primer_demo.business;

import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.business.exceptions.UsuarioNotExist;
import com.example.primer_demo.persistance.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMgr {


    @Autowired
    private AdminRepository adminRepository;



    public boolean ingresar(String nombre, String contrasena) throws InvalidInformation, UsuarioNotExist {

        if(nombre == null || "".equals(nombre) || contrasena == null || "".equals(contrasena)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(!adminRepository.existsById(nombre)){
            throw new UsuarioNotExist("El nombre de usuario no existe");
        }
        if(adminRepository.findById(nombre).get().getContrasena().equals(contrasena)){
            return true;
        }
        return false;
    }







}
