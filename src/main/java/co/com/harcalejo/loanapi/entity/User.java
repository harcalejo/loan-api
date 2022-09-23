package co.com.harcalejo.loanapi.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "target_id")
    private Target target;
}
