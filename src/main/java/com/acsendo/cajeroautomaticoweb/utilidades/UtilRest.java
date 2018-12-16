package com.acsendo.cajeroautomaticoweb.utilidades;

import com.acsendo.cajeroautomaticoweb.constantes.IConstantes;
import com.acsendo.cajeroautomaticoweb.dto.ParametrosRestDTO;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UtilRest {
	
    private static final Logger log= Logger.getLogger(UtilRest.class);

    private static final Gson GSON= getGson();

    public enum Method{
            POST,
            GET,
            PUT
    }
   
    public static Gson getGson(){
        return getGsonBuilder().create();
    }
	
	
    protected static GsonBuilder getGsonBuilder(){

        JsonSerializer<Date> ser = new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                        return src == null ? null : new JsonPrimitive(src.getTime());
                }
        };

        JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                                throws JsonParseException {
                        return json == null ? null : new Date(json.getAsLong());
                }
        };

        return new GsonBuilder().registerTypeAdapter(Date.class, ser).registerTypeAdapter(Date.class, deser);
	   
    }
   
    public static String getJsonFromObjet(Object o){
        try{
            if(o!=null){
                return GSON.toJson(o);
            }
        }catch(Exception e){
            log.error(" Error obteniendo el toString del objeto", e);
        }
        return null;
    }
    
    public static ParametrosRestDTO getParametrosRestDTO(UtilRest.Method metodo, String operacion){
        ParametrosRestDTO parametros = new ParametrosRestDTO();
        parametros.setUrl(IConstantes.URL_SERVICE+operacion);
        parametros.setConnectTimeOut(IConstantes.CONEXION_TIMEOUT);
        parametros.setReadTimeOut(IConstantes.READ_TIMEOUT);
        parametros.setMethod(metodo);
        return parametros;
    }
   
}
