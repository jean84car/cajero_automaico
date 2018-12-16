package com.acsendo.cajeroautomaticoweb.dto;

import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;
import java.io.Serializable;
import java.math.BigDecimal;


public class RetirarBilletesSolicitudDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal valorRetirar;

    public BigDecimal getValorRetirar() {
            return valorRetirar;
    }

    public void setValorRetirar(BigDecimal valorRetirar) {
            this.valorRetirar = valorRetirar;
    }

    @Override
    public String toString(){
        return UtilRest.getJsonFromObjet(this);
    }

}
