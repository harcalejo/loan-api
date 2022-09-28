package co.com.harcalejo.loanapi.entity;

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
}
