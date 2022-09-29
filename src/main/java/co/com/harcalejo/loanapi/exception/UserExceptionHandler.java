package co.com.harcalejo.loanapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class UserExceptionHandler {

    /**
     *
     * @param userException excepcion generada que ha sido atrapada
     *                      por el manejador
     * @return objeto que permite presentar al cliente una respuesta
     * resumida al preentarse una excepcion en el sistema
     */
    @ExceptionHandler(value = UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleUserException(UserException userException) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDate.now(),
                userException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(Exception exception) {
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDate.now(),
                exception.getMessage());
    }
}
