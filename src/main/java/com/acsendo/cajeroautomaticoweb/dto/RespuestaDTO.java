package com.acsendo.cajeroautomaticoweb.dto;

import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;


public class RespuestaDTO extends RespuestaRestDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean exito;
    private String mensaje;

    public boolean isExito() {
            return exito;
    }

    public void setExito(boolean exito) {
            this.exito = exito;
    }

    public String getMensaje() {
            return mensaje;
    }

    public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
    }

    @Override
    public String toString(){
        return UtilRest.getJsonFromObjet(this);
    }
	
}
