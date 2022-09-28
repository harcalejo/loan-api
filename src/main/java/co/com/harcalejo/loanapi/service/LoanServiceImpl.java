package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.RequestLoanPayloadDTO;
import co.com.harcalejo.loanapi.dto.RequestLoanResponseDTO;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.LoanProperties;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.repository.LoanRepository;
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
     * Bean para acceder a las capacidades del servicio de cálculos
     */
    private final CalculationService calculationService;

    /**
     * Bean para el manejo de properties de la API
     */
    private final LoanProperties loanProperties;

    /**
     * Constructor de la clase de implementación del servicio de Préstamos.
     *
     * @param loanRepository     dependencia del repositorio de Préstamo
     * @param userService        dependencia del servicio de Usuarios
     * @param calculationService dependecia del servicio de Cálculos
     * @param loanProperties depedencia del bean de properties
     */
    public LoanServiceImpl(LoanRepository loanRepository, UserService userService, CalculationService calculationService, LoanProperties loanProperties) {
        this.loanRepository = loanRepository;
        this.userService = userService;
        this.calculationService = calculationService;
        this.loanProperties = loanProperties;
    }

    @Override
    public RequestLoanResponseDTO requestLoan(
            RequestLoanPayloadDTO requestLoanPayloadDTO) throws UserException {

        final User user = userService
                .getUser(requestLoanPayloadDTO.getUserId());
        final UserTargetDTO userTargetDTO =
                loanProperties
                        .loadUserTargetProperties(
                                user.getTarget().getId());

        if(isAmountGreaterThanMaxAmount(
                requestLoanPayloadDTO, userTargetDTO)) {
            throw new UserException("El usuario supera el monto permitido");
        }

        Loan loanFromPayload = requestLoanPayloadDTO.toEntity();
        loanFromPayload.setUser(user);
        loanFromPayload.setCreationDate(LocalDate.now());
        loanFromPayload.setInstallment(
                calculationService
                        .calculateLoanInstallment(loanFromPayload));

        Loan loanFromSave = loanRepository.save(loanFromPayload);

        return new RequestLoanResponseDTO(loanFromSave);
    }

    private boolean isAmountGreaterThanMaxAmount(
            RequestLoanPayloadDTO requestLoanPayloadDTO, UserTargetDTO userTargetDTO) {
        return userTargetDTO.getMaxAmount() <
                requestLoanPayloadDTO.getAmount();
    }

    /**
     * Consulta los préstamos solicitados el último año por el usuario
     *
     * @return Listado de préstamos
     */
    private List<Loan> getLoansFromLastYear(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastYear = currentDate.minusYears(1);

        return loanRepository
                .findByIdAndCreationDateBetween(user.getId(), currentDate, lastYear);

    }

}
