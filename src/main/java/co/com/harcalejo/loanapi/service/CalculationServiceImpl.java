package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.LoanProperties;
import org.springframework.stereotype.Service;

/**
 * La clase {@code CalculationServiceImpl} es la implementación de la interfaz
 * {@link CalculationService}.
 *
 * Service - Anotación del framework Spring que permite definir el bean y
 * su prototipo.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Service
public class CalculationServiceImpl implements CalculationService{

    private final LoanProperties loanProperties;

    public CalculationServiceImpl(LoanProperties loanProperties) {
        this.loanProperties = loanProperties;
    }

    @Override
    public double calculateLoanInstallment(Loan loan) {
        final long userTarget = loan.getUser().getTarget().getId();
        final double r = loanProperties.loadUserTargetProperties(userTarget)
                .getRate() / LoanProperties.MONTHS;

        return (r + r / (Math.pow(1 + r, loan.getTerm() - 1))) * loan.getAmount();
    }

}
