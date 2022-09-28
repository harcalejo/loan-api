package co.com.harcalejo.loanapi.rule;

import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.UserTarget;
import co.com.harcalejo.loanapi.exception.RuleException;

public class TargetUserRule {
    private static final double NEW_RATE = 0.15;
    private static final double NEW_MAX = 500000;
    private static final double FREQUENT_RATE = 0.10;
    private static final double FREQUENT_MAX = 1000000;
    private static final double PREMIUM_RATE = 0.05;
    private static final double PREMIUM_MAX = 5000000;

    public UserTargetDTO executeRule(Loan loan) throws RuleException {
        String quantityResult = new QuantityCondition()
                .when(loan.getLoanCount())
                .getValue();
        String totalResult = new TotalAmountCondition()
                .when(loan.getTotalAmount())
                .getValue();

        if(isTargetUser(
                quantityResult, totalResult,
                UserTarget.NEW.getValue())) {

            return createUserTargetDTO(NEW_RATE, NEW_MAX);
        } else if(isTargetUser(
                quantityResult, totalResult,
                UserTarget.FREQUENT.getValue())) {

            return createUserTargetDTO(FREQUENT_RATE, FREQUENT_MAX);
        } else if(isTargetUser(
                quantityResult, totalResult,
                UserTarget.PREMIUM.getValue())) {

            return createUserTargetDTO(PREMIUM_RATE, PREMIUM_MAX);
        } else
            throw new RuleException("El usuario no cumple con las reglas");
    }

    private UserTargetDTO createUserTargetDTO(double rate, double maxAmount) {
        UserTargetDTO userTargetDTO = new UserTargetDTO();
        userTargetDTO.setRate(rate);
        userTargetDTO.setMaxAmount(maxAmount);

        return userTargetDTO;
    }

    private boolean isTargetUser(
            String quantityResult, String totalResult, String target) {
        return totalResult.compareTo(target) == 0 &&
                totalResult.compareTo(quantityResult) == 0;
    }

}
