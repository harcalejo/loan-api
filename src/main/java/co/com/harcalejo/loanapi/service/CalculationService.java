package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.entity.Loan;

/**
 * La interface {@code CalculationService} es el componente encargado de definir las
 * capacidades relacionadas a cálculos especializados en la capa de negocio.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
public interface CalculationService {

    /**
     * Realiza el calculo de la cuota del prestamo solicitado por el usuario,
     * teniendo en cuenta las reglas y los parametros del negocio.
     *
     * @param loan prestamo que se esta solicitando
     * @return el valor de la cuota calculado
     */
    double calculateLoanInstallment(Loan loan);
}
