package co.com.harcalejo.loanapi.dto;

import co.com.harcalejo.loanapi.entity.UserTarget;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserTargetDTO {
    private UserTarget userTarget;
    private double rate;
    private double maxAmount;
}
