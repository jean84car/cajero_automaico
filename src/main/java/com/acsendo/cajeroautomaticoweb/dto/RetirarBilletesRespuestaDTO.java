package com.acsendo.cajeroautomaticoweb.dto;

import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;

public class RetirarBilletesRespuestaDTO extends RespuestaDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String toString(){
        return UtilRest.getJsonFromObjet(this);
    }

}
