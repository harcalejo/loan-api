package co.com.harcalejo.loanapi.dto;

import co.com.harcalejo.loanapi.entity.UserTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * La clase {@code UserTargetDTO} nos permite definir los atributos que
 * caracterizan a los diferentes cliente objectivo.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserTargetDTO {
    /**
     * Enumeration que estandariza el nombre del Objetivo
     */
    private UserTarget userTarget;

    /**
     * Tasa de interes
     */
    private double rate;

    /**
     * Cantidad máxima a solicitar por préstamo
     */
    private double maxAmount;
}
