package co.com.harcalejo.loanapi.rule;

import co.com.harcalejo.loanapi.entity.UserTarget;
import co.com.harcalejo.loanapi.exception.RuleException;

public class TotalAmountCondition {
    private static final double NEW_TOTAL_AMOUNT = 100000;
    private static final double FREQUENT_TOTAL_AMOUNT = 500000;

    public UserTarget when(double totalAmount) throws RuleException {
        if (totalAmount < NEW_TOTAL_AMOUNT) {
            return UserTarget.NEW;
        } else if (totalAmount > NEW_TOTAL_AMOUNT &&
                totalAmount < FREQUENT_TOTAL_AMOUNT) {
            return UserTarget.FREQUENT;
        } else if (totalAmount > FREQUENT_TOTAL_AMOUNT) {
            return UserTarget.PREMIUM;
        } else
            throw new RuleException(
                    "No cumple la regla de monto total");
    }
}
