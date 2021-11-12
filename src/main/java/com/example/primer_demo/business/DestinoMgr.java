package com.example.primer_demo.business;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.Departamento;
import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Etiqueta;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.persistance.DestinoRespository;
import com.example.primer_demo.persistance.EtiquetasRepository;
import com.example.primer_demo.persistance.OperadorRepository;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DestinoMgr {

    @Autowired
    private DestinoRespository destinoRespository;

    @Autowired
    private EtiquetasRepository etiquetasRepository;

    @Autowired
    private OperadorRepository operadorRepository;

    public void agregarImagenes(Destino destino,List<byte[]> lista){
        for(byte[] x:lista){
            destino.addImages(x);
            destinoRespository.save(destino);
        }
    }


    public void agregarDestino(String nombre, String contacto, Integer aforo, LocalTime horario_apertura, LocalTime horario_cierre, String direccion, Departamento departamento, Operador operador, Set<Etiqueta> etiquetas, String desc, byte[] imagenes) throws InvalidInformation, IOException {

        if(nombre == null || "".equals(nombre) || contacto == null || "".equals(contacto) || operador == null || "".equals(operador)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(destinoRespository.findByNombre(nombre) != null){
            showAlert("Destino ya ingresado", "El destino ya ha sido registrado en el sistema");
        }


        Destino nuevoDestino = new Destino(nombre,contacto,aforo,horario_apertura,horario_cierre,direccion,departamento,operador, desc, imagenes);
        nuevoDestino.addEtiquetas(etiquetas);
        destinoRespository.save(nuevoDestino);
        for(Etiqueta x: etiquetas){
            x.addDestino(nuevoDestino);
            etiquetasRepository.save(x);
        }

    }

    public Boolean existeDestino(String nombre){
        return destinoRespository.existsByNombre(nombre);
    }

    public void bloquearDestino(Destino destino){
        destino.setHabilitada(false);
        destinoRespository.save(destino);
    }

    public void desbloquearDestino(Destino destino){
        destino.setHabilitada(true);
        destinoRespository.save(destino);
    }

    public void bloquearDestinosParaOperador(Operador operador){
        for(Destino destino: destinoRespository.findAllByOperador(operador)){
            destino.setHabilitada(false);
            destinoRespository.save(destino);
        }
    }

    public void desbloquearDestinoParaOperador(Operador operador){
        for(Destino destino: destinoRespository.findAllByOperador(operador)){
            destino.setHabilitada(true);
            destinoRespository.save(destino);
        }
    }

    public Iterable<Destino> listaDestinosOperador(Operador operador){
        return destinoRespository.findAllByOperador(operador);
    }

    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public Iterable<Destino> filtroDeBusqueda(String filtro){
        return destinoRespository.findAllByNombreContaining(filtro);
    }

    public Iterable<Destino> allDestinos(){
        return destinoRespository.findAll();
    }
}
