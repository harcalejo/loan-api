package co.com.harcalejo.loanapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private double amount;
    private int term;
    private double installment;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
