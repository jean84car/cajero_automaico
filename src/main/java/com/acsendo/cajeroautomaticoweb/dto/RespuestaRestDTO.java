package com.acsendo.cajeroautomaticoweb.dto;

import java.io.Serializable;

public class RespuestaRestDTO implements Serializable  {

	private static final long serialVersionUID = -5432332011616424849L;
	
	private Integer estadoHTTP;
	private Boolean exitoso;
	private String json;

	public Integer getEstadoHTTP() {
		return estadoHTTP;
	}

	public void setEstadoHTTP(Integer estadoHTTP) {
		this.estadoHTTP = estadoHTTP;
	}

	public Boolean isExitoso() {
		return exitoso;
	}

	public void setExitoso(Boolean exitoso) {
		this.exitoso = exitoso;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
		
}

