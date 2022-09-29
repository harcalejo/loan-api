package co.com.harcalejo.loanapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Clase utilitaria para la transformacion de objetos en cadenas
 * json.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
public class ObjectToJson {

    /**
     * Metodo que realiza el mapeo del objeto a un String Json
     *
     * @param object objeto que se desea transformar
     * @return String que representa el objeto en formato Json
     * @throws JsonProcessingException en caso de que el objeto no pueda ser transformado
     */
    public String transform(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();

        return objectWriter.writeValueAsString(object);
    }
}
