package co.com.harcalejo.loanapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * La clase {@code User} nos permite representar la entidad de negocio Usuario
 * como un objeto en nuestra API. Esta entidad de negocio contiene la información
 * de un Usuario y su relación con la entidad Target.
 *
 * Nota: Para efectos practicos del ejercicio, esta clase contiene información
 * básica, ya que esta entidad no es el foco del ejercicio.
 *
 * Entity - Anotación del framework de persistencia para definir el estereotipo de
 * la clase como entidad.
 *
 * NoArgsConstructor - Anotación de la librería Lombok usada para definir que la
 * clase tiene un constructor sin argumentos, para que sea generado en tiempo
 * de compilación y así mantener nuestro código simple.
 *
 * Getter - Anotación de la librería Lombok usada para definir que los atributos
 * de la clase deben tener asociado un método {@code Get}, para que sean generados
 * en tiempo de compilación y así mantener nuestro código simple.
 *
 * Setter - Anotación de la librería Lombok usada para definir que los atributos
 * de la clase deben tener asociado un método {@code Get}, para que sean generados
 * en tiempo de compilación y así mantener nuestro código simple.
 * clase tiene métodos
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
public class User {

    /**
     * Identificador único de la entidad
     *
     * Id - Anotación del framework de persistencia para determinar la llave primaria.
     */
    @Id
    private Long id;

    /**
     * Target representa la clasicación del Usuario beneficiario del préstamo
     *
     * ManyToOne - Anotación del framework de persistencia que determina el
     * tipo de relación entre el Usuario y el Target, en este caso, un
     * Usuario esta asociado a un Target y un Target puede tener asociados
     * varios Usuarios.
     *
     * JoinColumn - Anotación del framework de persistencia que determina la
     * columna que relaciona al Target con el Usuario, en este caso, el
     * identificador de único del Target.
     */
    @ManyToOne
    @JoinColumn(name = "target_id")
    private Target target;
}
