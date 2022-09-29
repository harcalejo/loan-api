package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.config.LoanProperties;
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

    /**
     * Constante para realizar el redondeo de un double a dos cifras decimales
     *
     * {@code Math.round(value * 100.0) / 100.0}
     */
    private static final double ROUND_DOUBLE_CONSTANT = 100.0;

    /**
     * Bean para acceder a las capacidades del servicio de Usuario
     */
    private final UserService userService;

    /**
     * @param userService dependencia del servicio de Usuario
     */
    public CalculationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public double calculateLoanInstallment(Loan loan) {
        final long userTarget = loan.getUser().getTarget().getId();
        final double r = userService.loadUserTargetProperties(userTarget)
                .getRate() / LoanProperties.MONTHS;

        return Math.round(
                ((r + r / (Math.pow(1 + r, loan.getTerm() - 1)))
                        * loan.getAmount())
                        * ROUND_DOUBLE_CONSTANT)/ROUND_DOUBLE_CONSTANT;
    }

}
