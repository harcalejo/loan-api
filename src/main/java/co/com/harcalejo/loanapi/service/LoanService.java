package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.exception.UserException;

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

}
