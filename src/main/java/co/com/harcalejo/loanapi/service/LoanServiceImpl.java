package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.RequestLoanPayloadDTO;
import co.com.harcalejo.loanapi.dto.RequestLoanResponseDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.repository.LoanRepository;
import co.com.harcalejo.loanapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * Objeto para interactuar con la base de datos de la entidad de Usuario
     */
    private final UserRepository userRepository;

    /**
     * Objeto para interactuar con la base de datos de la entidad de Préstamo
     */
    private final LoanRepository loanRepository;

    /**
     * Constructor de la clase de implementación del servicio de Préstamos.
     *
     * @param userRepository dependencia del repositorio de Usuario
     * @param loanRepository dependencia del repositorio de Préstamo
     */
    public LoanServiceImpl(UserRepository userRepository,
                           LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }


    @Override
    public RequestLoanResponseDTO requestLoan(
            RequestLoanPayloadDTO requestLoanPayloadDTO) throws UserException {

        User user = queryUserOwner(requestLoanPayloadDTO.getUserId());
        Loan loanFromPayload = requestLoanPayloadDTO.toEntity();
        loanFromPayload.setUser(user);

        Loan loanFromSave = loanRepository.save(loanFromPayload);

        return new RequestLoanResponseDTO(loanFromSave);
    }

    /**
     * Método encargado de realizar la consulta del usuario usando el repositorio
     * {@link UserRepository}
     *
     * @param userId identificador único del usuario
     * @return retorna la entidad del Usuario {@link User}
     * @throws UserException se genera si el userId no corresponde a un Usuario
     */
    private User queryUserOwner(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new UserException(
                "El usuario con Id " + userId + ", no existe"));

        return user.get();
    }


}
