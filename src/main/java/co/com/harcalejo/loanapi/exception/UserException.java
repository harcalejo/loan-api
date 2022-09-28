package co.com.harcalejo.loanapi.exception;

import lombok.Getter;

/**
 * La clase {@code UserException} es el componente encagado de las excepciones
 * customizadas relacionadas con el Usuario.
 *
 * extends {@link Exception}
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Getter
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }
}
