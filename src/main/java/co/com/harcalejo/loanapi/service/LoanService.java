package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.exception.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * La interface {@code LoanService} es el componente encargado de definir las
 * capacidades del servicio de Préstamos. De esta forma, buscamos que sólo se
 * maneje lógica especializada de Préstamos.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
public interface LoanService {

    /**
     * Este método permite la creación de un nuevo préstamo
     *
     * @param createLoanRequestDTO información del Préstamo y el Usuario que
     *                              solicita el préstamo.
     * @return retorna el identifador del préstamo y la cuota mensual.
     */
    CreateLoanResponseDTO createLoan(
            CreateLoanRequestDTO createLoanRequestDTO) throws UserException;

    /**
     * Permite obtener una lista de prestamos usando capacidades de paginacion
     *
     * @param starDate rango inferior de la consulta
     * @param endDate rango superior de la consulta
     * @param pageable objeto del framework que nos ayuda a soportar la
     *                 paginacion de registros. {@link Pageable}
     * @return sublista de prestamos soportada por el framework para la
     * paginacion. {@link Page}
     */
    Page<Loan> getLoansByRangeOfTime(
            LocalDate starDate, LocalDate endDate, Pageable pageable);
}