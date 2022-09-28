package co.com.harcalejo.loanapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * La clase {@code Loan} nos permite representar la entidad de negocio Préstamo
 * como un objeto en nuestra API. Esta entidad de negocio contiene la información
 * de un Préstamo y su relación  con el Usuario beneficiario del Préstamo.
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
 * de la clase deben tener asociado un método {@code Set}, para que sean generados
 * en tiempo de compilación y así mantener nuestro código simple.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Loan {

    /**
     * Identificador único de la entidad
     *
     * Id - Anotación del framework de persistencia para determinar la llave primaria.
     * GeneratedValue - Anotación del framework de persistencia que nos permite determinar
     * la estrategía de generación de esta llave primaria, en este caso {@code SEQUENCE}
     * determina que se usa una secuencía
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    /**
     * Monto del préstamo
     */
    private double amount;

    /**
     * Número de cuotas
     */
    private int term;

    /**
     * Cuota mensual del préstamo
     */
    private double installment;

    /**
     * Fecha de creación del prestamo.
     */
    private LocalDate creationDate;

    /**
     * Usuario beneficiario del préstamo
     *
     * ManyToOne - Anotación del framework de persistencia que determina el
     * tipo de relación entre el Usuario y el Préstamos, en este caso, un
     * Préstamo esta asociado a un Usuario y un Usuario puede tener asociados
     * varios Préstamos.
     *
     * JoinColumn - Anotación del framework de persistencia que determina la
     * columna que relaciona al Usuario con el Préstamo, en este caso, el
     * identificador de único del Usuario.
     */
    @ManyToOne
    @JoinColumn(name = "user_loan", referencedColumnName = "id")
    private User user;

    /**
     * Cantidad de prestamos solicitados
     */
    @Transient
    private long loanCount;

    /**
     * Volumen total de prestamos pedidos el último año
     */
    @Transient
    private double totalAmount;
}
