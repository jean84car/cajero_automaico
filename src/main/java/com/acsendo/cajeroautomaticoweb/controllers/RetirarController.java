/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acsendo.cajeroautomaticoweb.controllers;

import com.acsendo.cajeroautomaticoweb.constantes.IConstantes;
import com.acsendo.cajeroautomaticoweb.dto.RetirarBilletesRespuestaDTO;
import com.acsendo.cajeroautomaticoweb.dto.RetirarBilletesSolicitudDTO;
import com.acsendo.cajeroautomaticoweb.utilidades.ClientHttpServiceFacade;
import com.acsendo.cajeroautomaticoweb.utilidades.UtilFaces;
import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author Jean
 */
@ManagedBean
@ViewScoped
public class RetirarController implements Serializable{
    private BigDecimal valorRetirar;

    private static final Logger log = Logger.getLogger(RetirarController.class);
    
    public BigDecimal getValorRetirar() {
        return valorRetirar;
    }

    public void setValorRetirar(BigDecimal valorRetirar) {
        this.valorRetirar = valorRetirar;
    }

    public void retirar(){
        if(valorRetirar == null || valorRetirar.compareTo(BigDecimal.ZERO) <= 0){
            UtilFaces.addMessage("Error consultando las denominaciones", FacesMessage.SEVERITY_ERROR);
            return;
        }
        
        try{
            RetirarBilletesSolicitudDTO solicitud = new RetirarBilletesSolicitudDTO();
            solicitud.setValorRetirar(this.valorRetirar);
            log.info("RETIRAR :: REQUEST :: "+solicitud);
            RetirarBilletesRespuestaDTO respuesta = ClientHttpServiceFacade.send(UtilRest.getParametrosRestDTO(UtilRest.Method.PUT, IConstantes.OP_REGISTRAR_DENOMINACION), solicitud, RetirarBilletesRespuestaDTO.class);
            log.info("RETIRAR :: RESPONSE :: "+respuesta);
            if(respuesta.isExito()){
                UtilFaces.addMessage(respuesta.getMensaje(), FacesMessage.SEVERITY_INFO);
            }else{
                UtilFaces.addMessage(respuesta.getMensaje() != null ? respuesta.getMensaje() : "No fue posible realizar el retiro", FacesMessage.SEVERITY_ERROR);
            }
        }catch(Exception e){
            UtilFaces.addMessage("Error al intentar realizar el retiro", FacesMessage.SEVERITY_ERROR);
        }           
        
        valorRetirar = null;
    }
    
}


