package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.repository.UserRepository;

/**
 * La interface {@code UserService} es el componente encargado de definir las
 * capacidades del servicio de Usuarios en la capa de negocio. De esta forma,
 * buscamos que sólo se maneje lógica especializada de Usuarios.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
public interface UserService {

    /**
     * Método encargado de realizar la consulta del usuario usando el repositorio
     * {@link UserRepository}
     *
     * @param userId identificador único del usuario
     * @return retorna la entidad del Usuario {@link User}
     * @throws UserException se genera si el userId no corresponde a un Usuario
     *                       existente.
     */
    User getUser(Long userId) throws UserException;

    /**
     * Método encargado de realizar la carga de propiedades del negocio dependiendo
     * del tipo de Objetivo(Target).
     *
     * @param userTarget identificador unico del Target
     * @return un DTO con los datos cargados
     */
    UserTargetDTO loadUserTargetProperties(long userTarget);
}
