package co.com.harcalejo.loanapi.dto;

import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.Target;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class LoanByIdResponseDTO {
    /**
     * Identificador unico del prestamo
     */
    private Long id;

    /**
     * Monto del prestamo
     */
    private double amount;

    /**
     * Cuota mensual del préstamo
     */
    private double installment;

    /**
     * Numero de cuotas del prestamo
     */
    private int term;

    /**
     * Identificador unico del usuario dueño del préstamo
     */
    private long user_id;

    /**
     * Tipo de usuario Objetivo
     */
    private Target target;

    /**
     * Fecha de creacion del prestamo
     *
     * DateTimeFormat - Permite definir el formato de la fecha {@link DateTimeFormat}
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public LoanByIdResponseDTO(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getAmount();
        this.installment = loan.getInstallment();
        this.term = loan.getTerm();
        this.user_id = loan.getUser().getId();
        this.target = loan.getUser().getTarget();
        this.date = loan.getCreationDate();
    }
}
