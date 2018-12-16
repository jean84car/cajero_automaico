package com.acsendo.cajeroautomaticoweb.dto;

import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;
import java.util.List;

public class RegistrarDenominacionRespuestaDTO extends RespuestaDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<DenominacionDTO> denominaciones;


    public List<DenominacionDTO> getDenominaciones() {
            return denominaciones;
    }

    public void setDenominaciones(List<DenominacionDTO> denominaciones) {
            this.denominaciones = denominaciones;
    }

    @Override
    public String toString(){
        return UtilRest.getJsonFromObjet(this);
    }
	
}
