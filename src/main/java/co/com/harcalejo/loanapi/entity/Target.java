package co.com.harcalejo.loanapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * La clase {@code Target} nos permite representar la entidad de negocio Target
 * como un objeto en nuestra API. Esta entidad de negocio contiene la clasificación
 * de un Usuario dependiendo de su comportamiento en la adquisión de Préstamos.
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
@Getter
@Setter
public class Target {

    /**
     * Identificador único de la entidad
     *
     * Id - Anotación del framework de persistencia para determinar la llave primaria.
     */
    @Id
    private Long id;

    /**
     * Nombre con el que se identifica el Target del Usuario a nivel de negocio.
     */
    private String name;
}
