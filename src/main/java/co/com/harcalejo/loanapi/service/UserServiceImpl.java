package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.config.LoanProperties;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * La clase {@code UserServiceImpl} es la implementación de la interfaz
 * {@link UserService}.
 *
 * Service - Anotación del framework Spring que permite definir el bean y
 * su prototipo.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Bean para interactuar con la base de datos de la entidad de Usuario
     */
    private final UserRepository userRepository;

    /**
     * Bean de configuración para el manejo de parametros de negocio
     */
    private final LoanProperties loanProperties;

    /**
     * Constructor de la clase de implementación del servicio de Préstamos.
     *
     * @param userRepository dependencia del repositorio de Usuario
     * @param loanProperties dependencia de la configuracion de properties
     */
    public UserServiceImpl(UserRepository userRepository, LoanProperties loanProperties) {
        this.userRepository = userRepository;
        this.loanProperties = loanProperties;
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new UserException(
                "El usuario con Id " + userId + ", no existe"));

        return user.get();
    }

    @Override
    public UserTargetDTO loadUserTargetProperties(long userTarget) {
        UserTargetDTO userTargetDTO = new UserTargetDTO();

        if(isTarget(userTarget, LoanProperties.NEW_TARGET)) {
            userTargetDTO
                    .setRate(loanProperties.getNewRate());
            userTargetDTO
                    .setMaxAmount(loanProperties.getNewMaxAmount());
        } else if (isTarget(userTarget, LoanProperties.FREQUENT_TARGET)) {
            userTargetDTO
                    .setRate(loanProperties.getFrequentRate());
            userTargetDTO
                    .setMaxAmount(loanProperties.getFrequentMaxAmount());
        } else if (isTarget(userTarget, LoanProperties.PREMIUM_TARGET)) {
            userTargetDTO
                    .setRate(loanProperties.getPremiumRate());
            userTargetDTO
                    .setMaxAmount(loanProperties.getPremiumMaxAmount());
        }

        return userTargetDTO;
    }

    /**
     * Compara el id del target asignado al usuario con un el id del target enviado
     *
     * @param userTarget id del target asignado al Usuario
     * @param target id del target que se desea comparar
     * @return indica si los dos valores son iguales
     */
    private boolean isTarget(long userTarget, int target) {
        return userTarget == target;
    }
}
