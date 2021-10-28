package com.example.primer_demo.business;

import com.example.primer_demo.business.entities.Destino;
import com.example.primer_demo.business.entities.Operador;
import com.example.primer_demo.business.exceptions.InvalidInformation;
import com.example.primer_demo.persistance.DestinoRespository;
import com.example.primer_demo.persistance.OperadorRepository;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.Set;

@Service
public class DestinoMgr {

    @Autowired
    private DestinoRespository destinoRespository;

    @Autowired
    private OperadorRepository operadorRepository;


    public void agregarDestino(String nombre, String contacto, Operador operador) throws InvalidInformation {

        if(nombre == null || "".equals(nombre) || contacto == null || "".equals(contacto) || operador == null || "".equals(operador)){
            throw new InvalidInformation("Alguno de los datos ingresados no es correcto");
        }

        if(destinoRespository.findByNombre(nombre) != null){
            showAlert("Destino ya ingresado", "El destino ya ha sido registrado en el sistema");
        }




    }

    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public Iterable<Destino> allDestinos(){
        return destinoRespository.findAll();
    }
}
