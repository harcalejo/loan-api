package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.RequestLoanPayloadDTO;
import co.com.harcalejo.loanapi.dto.RequestLoanResponseDTO;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.exception.RuleException;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.repository.LoanRepository;
import co.com.harcalejo.loanapi.rule.TargetUserRule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * La clase {@code LoanServiceImpl} es la implementación de la interfaz
 * {@link LoanService}.
 *
 * Service - Anotación del framework Spring que permite definir el bean y
 * su prototipo.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Service
public class LoanServiceImpl implements LoanService {

    /**
     * Bean para interactuar con la base de datos de la entidad de Préstamo
     */
    private final LoanRepository loanRepository;

    /**
     * Bean para acceder a las capacidades del servicio de Usuario
     */
    private final UserService userService;

    /**
     * Constructor de la clase de implementación del servicio de Préstamos.
     *
     * @param loanRepository dependencia del repositorio de Préstamo
     * @param userService dependencia del servicio de Usuarios
     */
    public LoanServiceImpl(LoanRepository loanRepository, UserService userService) {
        this.loanRepository = loanRepository;
        this.userService = userService;
    }

    @Override
    public RequestLoanResponseDTO requestLoan(
            RequestLoanPayloadDTO requestLoanPayloadDTO) throws UserException, RuleException {

        User user = userService.getUser(requestLoanPayloadDTO.getUserId());
        Loan loanFromPayload = requestLoanPayloadDTO.toEntity();
        loanFromPayload.setUser(user);
        loanFromPayload.setCreationDate(LocalDate.now());
        loanFromPayload.setInstallment(calculateInstallment(loanFromPayload));

        Loan loanFromSave = loanRepository.save(loanFromPayload);

        return new RequestLoanResponseDTO(loanFromSave);
    }

    private double calculateInstallment(Loan loan) throws RuleException {
        List<Loan> loans = getLoansFromLastYear();
        long loansLastYear = loans
                .size();
        loan.setLoanCount(loansLastYear);

        double totalAmount = loans
                .stream()
                .mapToDouble(Loan::getAmount)
                .sum();

        loan.setTotalAmount(totalAmount);

        TargetUserRule rule = new TargetUserRule();
        UserTargetDTO userTargetDTO = rule.executeRule(loan);

        double r = userTargetDTO.getRate();

        return (r + r / (Math.pow(1 + r, loan.getTerm() - 1))) * loan.getAmount();
    }

    private List<Loan> getLoansFromLastYear() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastYear = currentDate.minusYears(1);

        return loanRepository
                .findByCreationDateBetween(currentDate, lastYear);

    }


}
