package co.com.harcalejo.loanapi.dto;

import co.com.harcalejo.loanapi.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Data
public class LoanByRangeResponseDTO {

    /**
     * Identificador unico del prestamo
     */
    private Long id;

    /**
     * Monto del prestamo
     */
    private double amount;

    /**
     * Numero de cuotas del prestamo
     */
    private int term;

    /**
     * Identificador unico del usuario dueño del préstamo
     */
    private long user_id;

    /**
     * Nombre del tipo de usuario Objetivo
     */
    private String target;

    /**
     * Fecha de creacion del prestamo
     *
     * DateTimeFormat - Permite definir el formato de la fecha {@link DateTimeFormat}
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public LoanByRangeResponseDTO(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getAmount();
        this.term = loan.getTerm();
        this.user_id = loan.getUser().getId();
        this.target = loan.getUser().getTarget().getName();
        this.date = loan.getCreationDate();
    }
}
