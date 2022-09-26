package co.com.harcalejo.loanapi.dto;

import co.com.harcalejo.loanapi.entity.Loan;
import lombok.Getter;
import lombok.Setter;

/**
 * La clase {@code RequestLoanPayloadDTO} nos permite definir los atributos de la
 * entidad de Préstamo que requerimos en la solicitud de un nuevo Préstamo.
 *
 * Getter - Anotación de la librería Lombok usada para definir que los atributos
 * de la clase deben tener asociado un método {@code Get}, para que sean generados
 * en tiempo de compilación y así mantener nuestro código simple.
 *
 * Setter - Anotación de la librería Lombok usada para definir que los atributos
 * de la clase deben tener asociado un método {@code Set}, para que sean generados
 * en tiempo de compilación y así mantener nuestro código simple.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Setter
@Getter
public class RequestLoanPayloadDTO {
    /**
     * Monto del préstamo
     */
    private double amount;

    /**
     * Número de cuotas
     */
    private int term;

    /**
     * Identificador único del usuario
     */
    private Long userId;
}
