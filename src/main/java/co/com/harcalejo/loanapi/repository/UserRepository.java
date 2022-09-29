package co.com.harcalejo.loanapi.repository;

import co.com.harcalejo.loanapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * La interface {@code UserRepository} es el componente encagado del acceso a los
 * datos de nuestra API. Permitiendonos guardar, modificar, eliminar y consultar
 * registros de un Prestamos (Ver clase {@link User}).
 *
 * Nota: Para efectos practicos del ejercicio, solo usaremos la consulta de Usuarios,
 * ya que la creación, modificación y eliminación estan fuera del alcance.
 *
 * extiende de la clase {@link JpaRepository} que contiene la definición de los
 * métodos mencionados anteriormente y algunos adicionales. Además, con la ayuda
 * de el operador diamond hemos definido que la entidad que maneja el Repository
 * es de tipo {@link User} y el tipo de su llave primaria es Long.
 *
 * Repository - Anotación del framework de Spring para definir el estereotipo del
 * bean como Repositorio.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Permite consultar un Usuario
     *
     * @param id identificador único del Usuario
     * @return retorna el usuario correspondiente. Usamos la clase {@link Optional}
     * para controlar los casos donde el identificador del Usuario no corresponde
     * a uno de los Usuarios existentes.
     */
    Optional<User> findById(Long id);
}
