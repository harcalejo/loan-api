package co.com.harcalejo.loanapi.repository;

import co.com.harcalejo.loanapi.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * La interface {@code LoanRepository} es el componente encagado del acceso a los
 * datos de nuestra API. Permitiendonos guardar, modificar, eliminar y consultar
 * registros de un Prestamos (Ver clase {@link Loan}).
 *
 * extiende de la clase {@link JpaRepository} que contiene la definición de los
 * métodos mencionados anteriormente y algunos adicionales. Además, con la ayuda
 * de el operador diamond hemos definido que la entidad que maneja el Repository
 * es de tipo {@link Loan} y el tipo de su llave primaria es Long.
 *
 * Repository - Anotación del framework de Spring para definir el estereotipo del
 * bean como Repositorio.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
