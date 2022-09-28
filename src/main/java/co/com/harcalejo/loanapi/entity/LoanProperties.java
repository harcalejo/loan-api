package co.com.harcalejo.loanapi.entity;

import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("loan.properties")
@Getter
public class LoanProperties {
    public static final int NEW_TARGET = 1;
    public static final int FREQUENT_TARGET = 2;
    public static final int PREMIUM_TARGET = 3;

    public static final int MONTHS = 12;

    @Value("${target.new.rate}")
    private double newRate;
    @Value("${target.frequent.rate}")
    private double frequentRate;
    @Value("${target.premium.rate}")
    private double premiumRate;
    @Value("${target.new.max_amount}")
    private double newMaxAmount;
    @Value("${target.frequent.max_amount}")
    private double frequentMaxAmount;
    @Value("${target.premium.max_amount}")
    private double premiumMaxAmount;

    public UserTargetDTO loadUserTargetProperties(long userTarget) {
        UserTargetDTO userTargetDTO = new UserTargetDTO();

        if(isNewTarget(userTarget)) {
            userTargetDTO
                    .setRate(this.getNewRate());
            userTargetDTO
                    .setMaxAmount(this.getNewMaxAmount());
        } else if (isFrequentTarget(userTarget)) {
            userTargetDTO
                    .setRate(this.getFrequentRate());
            userTargetDTO
                    .setMaxAmount(this.getFrequentMaxAmount());
        } else if (isPremiumTarget(userTarget)) {
            userTargetDTO
                    .setRate(this.getPremiumRate());
            userTargetDTO
                    .setMaxAmount(this.getPremiumMaxAmount());
        }

        return userTargetDTO;
    }

    private boolean isPremiumTarget(long userTarget) {
        return userTarget ==
                PREMIUM_TARGET;
    }

    private boolean isFrequentTarget(long userTarget) {
        return userTarget ==
                FREQUENT_TARGET;
    }

    private boolean isNewTarget(long userTarget) {
        return userTarget ==
                NEW_TARGET;
    }
}
