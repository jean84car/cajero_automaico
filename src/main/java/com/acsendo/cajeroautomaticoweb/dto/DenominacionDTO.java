package com.acsendo.cajeroautomaticoweb.dto;

import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;
import java.io.Serializable;
import java.math.BigDecimal;

public class DenominacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
	
    private Long idDenominacion;	
    private BigDecimal denominacion;
    private Integer cantidad;

    
    public Long getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(Long idDenominacion) {
        this.idDenominacion = idDenominacion;
    }
    
    public BigDecimal getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(BigDecimal denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString(){
        return UtilRest.getJsonFromObjet(this);
    }
	
}
