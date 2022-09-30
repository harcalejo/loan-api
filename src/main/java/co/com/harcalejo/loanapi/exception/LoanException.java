package co.com.harcalejo.loanapi.exception;

/**
 * La clase {@code LoanException} es el componente encagado de las
 * excepciones customizadas relacionadas con el Prestamo.
 *
 * extends {@link RuntimeException}
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
public class LoanException extends RuntimeException {
    public LoanException(String message) {
        super(message);
    }
}
