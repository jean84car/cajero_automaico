/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acsendo.cajeroautomaticoweb.controllers;

import com.acsendo.cajeroautomaticoweb.constantes.IConstantes;
import com.acsendo.cajeroautomaticoweb.dto.DenominacionDTO;
import com.acsendo.cajeroautomaticoweb.dto.RegistrarDenominacionRespuestaDTO;
import com.acsendo.cajeroautomaticoweb.utilidades.ClientHttpServiceFacade;
import com.acsendo.cajeroautomaticoweb.utilidades.UtilFaces;
import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class RegistrarController implements Serializable{
    private DenominacionDTO denominacion;
    private List<DenominacionDTO> denominaciones;

    private static final Logger log = Logger.getLogger(RegistrarController.class);
    
    @PostConstruct
    public void init(){
        denominacion = new DenominacionDTO();
        try{            
            log.info("CONSULTA :: REQUEST :: "+denominacion);
            RegistrarDenominacionRespuestaDTO respuesta = ClientHttpServiceFacade.send(UtilRest.getParametrosRestDTO(UtilRest.Method.GET, IConstantes.OP_REGISTRAR_DENOMINACION), denominacion, RegistrarDenominacionRespuestaDTO.class);
            log.info("CONSULTA :: RESPONSE :: "+respuesta);
            if(respuesta.isExito() && respuesta.getDenominaciones() != null){
                denominaciones = respuesta.getDenominaciones();
            }else{
                denominaciones = new ArrayList<>();
            }
        }catch(Exception e){
            UtilFaces.addMessage("Error consultando las denominaciones", FacesMessage.SEVERITY_ERROR);
        }        
    }
        
    public DenominacionDTO getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(DenominacionDTO denominacion) {
        this.denominacion = denominacion;
    }

    public List<DenominacionDTO> getDenominaciones() {
        return denominaciones;
    }

    public void setDenominaciones(List<DenominacionDTO> denominaciones) {
        this.denominaciones = denominaciones;
    }
    
    /*
        metodo registrarDenominacion
        Envia solicitud al servicio para el registro de las denominaciones
    */
    public void registrarDenominacion(){
        try{
            log.info("REGISTRAR :: REQUEST :: "+denominacion);
            RegistrarDenominacionRespuestaDTO respuesta = ClientHttpServiceFacade.send(UtilRest.getParametrosRestDTO(UtilRest.Method.POST, IConstantes.OP_REGISTRAR_DENOMINACION), denominacion, RegistrarDenominacionRespuestaDTO.class);
            log.info("REGISTRAR :: RESPONSE :: "+respuesta);
            if(respuesta.isExito() && respuesta.getDenominaciones() != null){
                denominaciones = respuesta.getDenominaciones();
                UtilFaces.addMessage("Registro exitoso.", FacesMessage.SEVERITY_INFO);
            }else{
                UtilFaces.addMessage(respuesta.getMensaje() != null ? respuesta.getMensaje() : "No fue posible realizar el registro", FacesMessage.SEVERITY_ERROR);
            }
            
            denominacion = new DenominacionDTO();
        }catch(Exception e){
            UtilFaces.addMessage("Error al intentar realizar el registro", FacesMessage.SEVERITY_ERROR);
        }
    }
  
}


