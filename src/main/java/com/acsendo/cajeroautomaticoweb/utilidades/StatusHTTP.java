package com.acsendo.cajeroautomaticoweb.utilidades;

public enum StatusHTTP {
	EXITOSO(200),
	EXCEPCION_SISTEMA(500),
	SISTEMA_NO_DISPONIBLE(503);
	
	private final int status;
	
	private StatusHTTP(int status) {
		this.status= status;
	}

	public int getStatus() {
		return status;
	}
	
}
