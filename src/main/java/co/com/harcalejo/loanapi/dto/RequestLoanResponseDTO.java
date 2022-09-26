package co.com.harcalejo.loanapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * La clase {@code RequestLoanResponseDTO} nos permite definir los atributos de la
 * entidad de Préstamo que queremos transmitir a la capa cliente en la solicitud
 * de un nuevo Préstamo.
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
public class RequestLoanResponseDTO {
    /**
     * Identificador único del prestamo
     */
    private Long loanId;

    /**
     * Cuota mensual del préstamo
     */
    private int installment;
}
