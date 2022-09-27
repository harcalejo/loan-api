package co.com.harcalejo.loanapi.service;

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
     * Constructor de la clase de implementación del servicio de Préstamos.
     *
     * @param userRepository dependencia del repositorio de Usuario
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new UserException(
                "El usuario con Id " + userId + ", no existe"));

        return user.get();
    }
}
