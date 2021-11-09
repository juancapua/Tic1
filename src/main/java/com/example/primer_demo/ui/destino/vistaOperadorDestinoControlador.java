package com.example.primer_demo.ui.destino;

import com.example.primer_demo.business.entities.Destino;
import org.springframework.stereotype.Component;

@Component
public class vistaOperadorDestinoControlador {

    private Destino destino;

    public void setDestino(Destino destino){
        this.destino = destino;
    }

}
