package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
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
     * Constructor de la clase de implementación del servicio de Préstamos.
     *
     * @param loanRepository     dependencia del repositorio de Préstamo
     * @param userService        dependencia del servicio de Usuarios
     * @param calculationService dependecia del servicio de Cálculos
     */
    public LoanServiceImpl(LoanRepository loanRepository, UserService userService, CalculationService calculationService) {
        this.loanRepository = loanRepository;
        this.userService = userService;
        this.calculationService = calculationService;
    }

    @Override
    public CreateLoanResponseDTO createLoan(
            CreateLoanRequestDTO createLoanRequestDTO) {

        final User user = userService
                .getUser(createLoanRequestDTO.getUserId());
        final UserTargetDTO userTargetDTO =
                userService
                        .loadUserTargetProperties(
                                user.getTarget().getId());

        if(isAmountGreaterThanMaxAmount(
                createLoanRequestDTO, userTargetDTO)) {
            throw new UserException("El usuario supera el monto permitido");
        }

        return new CreateLoanResponseDTO(
                buildLoanForResponse(createLoanRequestDTO, user));
    }

    /**
     * Complementa el objeto prestamo para ser retornado, usando los datos
     * involucrados durante el proceso de creacion.
     *
     * @param createLoanRequestDTO valores de la solicitud recibidos
     * @param user usuario asociado a la solicitud
     * @return objeto Prestamo complementado
     */
    private Loan buildLoanForResponse(
            CreateLoanRequestDTO createLoanRequestDTO, User user) {
        Loan loanFromPayload = createLoanRequestDTO.toEntity();
        loanFromPayload.setUser(user);
        loanFromPayload.setCreationDate(LocalDate.now());
        loanFromPayload.setInstallment(
                calculationService
                        .calculateLoanInstallment(loanFromPayload));
        
        return loanRepository.save(loanFromPayload);
    }

    /**
     * Compara si el monto solicitado es mayor al maximo permitido
     *
     * @param createLoanRequestDTO atributos enviados en la solicitud del prestamo
     *                              entre ellos el monto solicitado
     * @param userTargetDTO valores cargados de las properties de negocio, donde se
     *                      define el valor maximo permitido
     * @return si el monto solicitidado es superior al permitido
     */
    private boolean isAmountGreaterThanMaxAmount(
            CreateLoanRequestDTO createLoanRequestDTO, UserTargetDTO userTargetDTO) {
        return userTargetDTO.getMaxAmount() <
                createLoanRequestDTO.getAmount();
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
