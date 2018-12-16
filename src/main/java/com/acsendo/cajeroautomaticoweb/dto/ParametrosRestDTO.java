package com.acsendo.cajeroautomaticoweb.dto;


import com.acsendo.cajeroautomaticoweb.utilidades.UtilRest;
import java.io.Serializable;
import java.util.Map;



 
public class ParametrosRestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private int readTimeOut;
	private int connectTimeOut;
	private Map<String, String> headers;
	private Map<String, String> parametros;
	private UtilRest.Method method;
	private boolean provocarTimeout;
	
	public int getConnectTimeOut() {
		return connectTimeOut;
	}
	
	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public UtilRest.Method getMethod() {
		return method;
	}

	public void setMethod(UtilRest.Method method) {
		this.method = method;
	}

	public boolean isProvocarTimeout() {
		return provocarTimeout;
	}

	public void setProvocarTimeout(boolean provocarTimeout) {
		this.provocarTimeout = provocarTimeout;
	}

	@Override
	public String toString(){
		return UtilRest.getJsonFromObjet(this);
	}
}
