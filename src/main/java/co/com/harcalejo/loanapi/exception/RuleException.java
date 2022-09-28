package co.com.harcalejo.loanapi.exception;

/**
 * La clase {@code UserException} es el componente encargado de las excepciones
 * customizadas relacionadas con las Reglas de negocio.
 *
 * extends {@link Exception}
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 *
 */
public class RuleException extends Exception {
    public RuleException(String message) {
        super(message);
    }
}
