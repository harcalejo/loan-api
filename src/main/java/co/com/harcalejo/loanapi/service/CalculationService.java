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

    double calculateLoanInstallment(Loan loan);
}
