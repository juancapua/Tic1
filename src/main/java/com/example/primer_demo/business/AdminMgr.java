package com.example.primer_demo.business;

import com.example.primer_demo.persistance.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMgr {


    @Autowired
    private AdminRepository adminRepository;


    public boolean ingresar(String nombre, String contrasena){

        if(adminRepository.findAdminByNombreUsuarioAndContrasena(nombre, contrasena) != null){
            return true;
        }
        return false;
    }







}
