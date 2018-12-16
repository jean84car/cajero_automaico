package com.acsendo.cajeroautomaticoweb.utilidades;


import com.acsendo.cajeroautomaticoweb.dto.ParametrosRestDTO;
import com.acsendo.cajeroautomaticoweb.dto.RespuestaRestDTO;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPut;
 

/**
 * Clase encargada de servir de facha para realizar el consumo de un servicio.
 */
public class ClientHttpServiceFacade {
	
	/** Referencia a log. */
	private static Logger log = Logger.getLogger(ClientHttpServiceFacade.class);
	
	private static final String MEDIA_TYPE= "application/json; charset=UTF-8";
	private static final String CHARSET= "UTF-8";
	
	public enum MensajesEnum{
            ERROR_CONEXION("Error de conexion, con el servicio REST."),
            ERROR_TIMEOUT("Error de time out, con el servicio REST."),
            ERROR_ENVIO("Error al enviar la solicitud al servicio.");

            private final String mensaje;

            private MensajesEnum(String mensaje) {
                this.mensaje= mensaje;
            }

            public String getMensaje() {
                return mensaje;
            }
		
	}

	/**
	 * Realiza la invocacion a la operacion.
	 * 
	 * @param ParametrosRestDTO
	 * @param object, objeto con la informacion de request.
	 * @param classResponse, Tipo de clase de respuesta.
	 * @return
	 * @throws Exception
	 */
	public static <T extends RespuestaRestDTO> T send(ParametrosRestDTO parametrosRest, Object object, Class<T> classResponse) throws Exception {
            T responseObject = null;
            try {
                Gson gson = UtilRest.getGson();			
                responseObject = sendHttpPost(gson.toJson(object), parametrosRest, classResponse);
            } catch (Exception e) {
                log.error("REST ERROR :: " + e.getMessage());
                log.error(e.getMessage()); 
                throw e;
            }
            return responseObject;
	}
	
	private static HttpRequestBase getHttpRequestBase(String json, ParametrosRestDTO parametrosRest) throws Exception {
            HttpRequestBase requestBase;
            StringEntity stringEntity = null;
            
            if(parametrosRest.getMethod() == null)
                parametrosRest.setMethod(UtilRest.Method.GET);
            if(json != null && !json.isEmpty()){
                stringEntity = new StringEntity(json, CHARSET);
                stringEntity.setContentType("application/json");
            }
            
            switch (parametrosRest.getMethod()) {
                case PUT:
                    HttpPut requestPut = new HttpPut(parametrosRest.getUrl());
                    requestPut.setEntity(stringEntity);
                    requestBase = requestPut;
                    break;
                case POST:
                    HttpPost postRequest = new HttpPost(parametrosRest.getUrl());
                    postRequest.setEntity(stringEntity);
                    requestBase = postRequest;
                    break;
                default:
                    URIBuilder builder = new URIBuilder(parametrosRest.getUrl());
                    builder.setCharset(Charset.forName(CHARSET));
                    if(parametrosRest.getParametros()!=null){
                        for(String key: parametrosRest.getParametros().keySet()){
                            builder.setParameter(key, parametrosRest.getParametros().get(key));
                        }    
                    }   HttpGet getRequest = new HttpGet(builder.build());
                    requestBase = getRequest;
                    break;
            }

            return requestBase;
	}
	
	/**
	 * Realiza una peticion al servicio REST.
	 * 
	 * @param json, datos enviados.
	 * @param url, URL del servicio.
	 * @param readTimeOut, tiempo de lectura maximo.
	 * @param connectTimeOut, tiempo de conexion maximo.
	 * @return
	 * @throws Exception 
	 */
	private static <T extends RespuestaRestDTO> T sendHttpPost(String json, ParametrosRestDTO parametrosRest, Class<T> classResponse) throws Exception{
		
            RequestConfig.Builder requestBuilder = RequestConfig.custom();
            requestBuilder = requestBuilder.setConnectTimeout(parametrosRest.getConnectTimeOut());
            requestBuilder = requestBuilder.setConnectionRequestTimeout(parametrosRest.getReadTimeOut());

            HttpClientBuilder builder = HttpClientBuilder.create();     
            builder.setDefaultRequestConfig(requestBuilder.build());

            HttpClient httpClient = builder.build();
            HttpRequestBase baseRequest= getHttpRequestBase(json, parametrosRest);

            baseRequest.setHeader("Content-Type", MEDIA_TYPE);
            if(parametrosRest.getHeaders()!=null && !parametrosRest.getHeaders().isEmpty()){
                for(String key:parametrosRest.getHeaders().keySet()){
                    baseRequest.addHeader(key, parametrosRest.getHeaders().get(key));
                }
            }

            HttpResponse response = null;

            try {
                response = httpClient.execute(baseRequest);
            } catch (ClientProtocolException e) {
                log.error(e.getMessage());
                if (e.getCause() instanceof SocketException) {
                    log.error(MensajesEnum.ERROR_CONEXION.getMensaje());
                }
                if (e.getCause() instanceof SocketTimeoutException) {
                    log.error(MensajesEnum.ERROR_TIMEOUT.getMensaje());
                }
                if (e.getCause() instanceof ConnectException) {
                    log.error(MensajesEnum.ERROR_CONEXION.getMensaje());
                }
                throw e;
            } catch (ConnectException e) {
                log.error(MensajesEnum.ERROR_CONEXION.getMensaje());
                log.error(e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error(MensajesEnum.ERROR_ENVIO.getMensaje());
                log.error(e.getMessage());
                throw e;
            }

            T responseObject= getResponse(response, classResponse);

            return responseObject;

	}
	
	private static <T extends RespuestaRestDTO> T getResponse(HttpResponse responseHttp, Class<T> classResponse) throws Exception {
            T responseObject = null;
            try {

                Gson gson = UtilRest.getGson();			

                StringBuilder jsonResponse= new StringBuilder();
                if(responseHttp.getEntity()!= null && responseHttp.getEntity().getContent()!= null){
                        HttpEntity entity = responseHttp.getEntity ();
                        entity.getContentEncoding (); //returns null
                        String respuesta = EntityUtils.toString(entity, Charset.forName(CHARSET));
                        jsonResponse.append(respuesta);				
                }

                log.info("REST :: RESPONSE :: "+jsonResponse.toString());
                if (responseHttp.getStatusLine().getStatusCode() != StatusHTTP.EXITOSO.getStatus()) {
                        log.info("REST :: ERROR :: status " + responseHttp.getStatusLine().getStatusCode());
                        try{
                                responseObject = gson.fromJson(jsonResponse.toString(), classResponse);
                                responseObject.setExitoso(false);
                        }catch(Exception e){
                                responseObject = classResponse.newInstance();
                                responseObject.setEstadoHTTP(responseHttp.getStatusLine().getStatusCode());
                                responseObject.setExitoso(Boolean.FALSE);
                                responseObject.setExitoso(false);
                        }
                        return responseObject;
                }

                responseObject = gson.fromJson(jsonResponse.toString(), classResponse);
                responseObject.setEstadoHTTP(responseHttp.getStatusLine().getStatusCode());
                responseObject.setExitoso(true);
                responseObject.setJson(jsonResponse.toString());
            } catch (Exception e) {
                    log.error("REST ERROR :: " + e.getMessage());
                    log.error(e.getMessage());
                    throw e;
            }

            return responseObject;
	}
	
}
