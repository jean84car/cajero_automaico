/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acsendo.cajeroautomaticoweb.utilidades;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jean
 */
public class UtilFaces {

    private UtilFaces() {
        //pendiente
    }
    
    public static void addMessage(String mensaje, FacesMessage.Severity tipoMensaje) {
        FacesMessage message = new FacesMessage(tipoMensaje, mensaje, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
