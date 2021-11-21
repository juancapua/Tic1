package com.example.primer_demo.business;

import com.example.primer_demo.PrimerDemoApplication;
import com.example.primer_demo.business.entities.*;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.persistance.DestinoRespository;
import com.example.primer_demo.persistance.DiaRepository;
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
    private DiaRepository diaRepository;

    @Autowired
    private OperadorRepository operadorRepository;

    public void agregarImagenes(Destino destino,List<byte[]> lista){
        for(byte[] x:lista){
            destino.addImages(x);
            destinoRespository.save(destino);
        }
    }


    public void agregarDestino(String nombre, String contacto, Integer aforo, LocalTime horario_apertura, LocalTime horario_cierre, String direccion, Departamento departamento, Operador operador, Set<Etiqueta> etiquetas, String desc, byte[] imagenes, Set<Dia> dias) throws InvalidInformation, IOException {

        if(nombre == null || "".equals(nombre) || contacto == null || "".equals(contacto) || operador == null || "".equals(operador)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(destinoRespository.findByNombre(nombre) != null){
            showAlert("Destino ya ingresado", "El destino ya ha sido registrado en el sistema");
        }


        Destino nuevoDestino = new Destino(nombre,contacto,aforo,horario_apertura,horario_cierre,direccion,departamento,operador, desc, imagenes, dias);
        nuevoDestino.addEtiquetas(etiquetas);
        destinoRespository.save(nuevoDestino);
        for(Etiqueta x: etiquetas){
            x.addDestino(nuevoDestino);
            etiquetasRepository.save(x);
        }
        for(Dia x: dias){
            x.addDestino(nuevoDestino);
            diaRepository.save(x);
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

    public void cambiarContacto(Destino destino, String contacto){
        destino.setContacto(contacto);
        destinoRespository.save(destino);
    }

    public void cambiarAforo(Destino destino, Integer aforo){
        destino.setAforo(aforo);
        destinoRespository.save(destino);
    }

    public void cambiarApertura(Destino destino, LocalTime hora){
        destino.setHorario_apertura(hora);
        destinoRespository.save(destino);
    }

    public void cambiarCierre(Destino destino, LocalTime hora){
        destino.setHorario_cierre(hora);
        destinoRespository.save(destino);
    }

    public void cambiarDescripcion(Destino destino, String desc){
        destino.setDescripcion(desc);
        destinoRespository.save(destino);
    }

    public void cambiarEtiquetas(Destino destino, Set<Etiqueta> etiquetas){
        Set<Etiqueta> etiquetasViejas = destino.getEtiquetas();
        destino.setEtiquetas(etiquetas);
        destinoRespository.save(destino);
        for(Etiqueta x: etiquetasViejas){
            x.deleteDestino(destino);
            etiquetasRepository.save(x);
        }
        for(Etiqueta x:etiquetas){
            x.addDestino(destino);
            etiquetasRepository.save(x);
        }
    }

    public void cambiarDias(Destino destino, Set<Dia> dias){
        Set<Dia> diasViejos = destino.getDias();
        destino.setDias(dias);
        destinoRespository.save(destino);
        for(Dia x: diasViejos){
            x.deleteDestino(destino);
            diaRepository.save(x);
        }
        for(Dia x: dias){
            x.addDestino(destino);
            diaRepository.save(x);
        }
    }

    public Iterable<Destino> buscarPorDepartamento(Departamento departamento){
         return destinoRespository.findAllByDepartamento(departamento);
    }
}
