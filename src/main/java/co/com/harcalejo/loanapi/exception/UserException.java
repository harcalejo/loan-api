package co.com.harcalejo.loanapi.exception;

/**
 * La clase {@code UserException} es el componente encagado de las excepciones
 * customizadas relacionadas con el Usuario.
 *
 * extends {@link Exception}
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
public class UserException extends Exception {

    public UserException(String message) {
        super(message);
    }
}
