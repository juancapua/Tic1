package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Experiencia;
import com.example.primer_demo.persistance.DestinoRespository;
import com.example.primer_demo.persistance.ExperienciaRepository;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ExperienciaMgr {

    @Autowired
    private ExperienciaRepository experienciaRepository;

    @Autowired
    private DestinoRespository destinoRespository;


    public void agregarExperiencia(String nombre, String descripcion, LocalTime horario_apertura, LocalTime horario_cierre, Integer aforo, Boolean se_reserva, Destino destino, String tipo, int duracion){

        for(Experiencia x:destino.getExperiencias()) {
            if (x.getNombre().equals(nombre)) {
                showAlert("Experiencia ya ingresado", "El destino ya ha sido registrado en el sistema");
            }
        }

        Experiencia nuevaExperiencia = new Experiencia(nombre, descripcion, horario_apertura,horario_cierre, aforo, se_reserva, destino, tipo, duracion);
        experienciaRepository.save(nuevaExperiencia);
        destino.addExperiencias(nuevaExperiencia);
    }

    public void bloquearExperiencia(Experiencia experiencia){
        experiencia.setEsta_autorizada(false);
        experienciaRepository.save(experiencia);
    }

    public void habilitarExperiencia(Experiencia experiencia){
        experiencia.setEsta_autorizada(true);
        experienciaRepository.save(experiencia);
    }


    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
}
