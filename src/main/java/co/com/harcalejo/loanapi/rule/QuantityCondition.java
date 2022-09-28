package co.com.harcalejo.loanapi.rule;

import co.com.harcalejo.loanapi.entity.UserTarget;
import co.com.harcalejo.loanapi.exception.RuleException;

public class QuantityCondition {
    private static final int NEW_MAX_QUANTITY = 2;
    private static final int NEW_MIN_QUANTITY = 0;
    private static final int FREQUENT_MAX_QUANTITY = 5;

    public UserTarget when(long quantity) throws RuleException {
        if (quantity >= NEW_MIN_QUANTITY &&
            quantity < NEW_MAX_QUANTITY ) {
           return UserTarget.NEW;
        } else if (quantity >= NEW_MAX_QUANTITY &&
                quantity < FREQUENT_MAX_QUANTITY) {
            return UserTarget.FREQUENT;
        } else if (quantity > FREQUENT_MAX_QUANTITY) {
            return UserTarget.PREMIUM;
        } else
            throw new RuleException(
                    "No cumple la regla de cantidad de prestamos");
    }

}
