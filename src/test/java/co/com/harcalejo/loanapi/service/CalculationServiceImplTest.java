package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.Target;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.entity.UserTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class CalculationServiceImplTest {
    @MockBean
    UserService userService;

    CalculationService calculationService;

    @BeforeEach
    void setup() {
        calculationService =
                new CalculationServiceImpl(userService);
    }

    @Test
    void calculateLoanInstallmentForNewUser() {
        //given
        Target targetNew = new Target();
        targetNew.setId(1L);
        targetNew.setName("NEW");

        User userNew = new User();
        userNew.setTarget(targetNew);
        userNew.setId(1L);

        UserTargetDTO newUserTargetDTO = new UserTargetDTO();
        newUserTargetDTO.setMaxAmount(500000);
        newUserTargetDTO.setRate(0.15);
        newUserTargetDTO.setUserTarget(UserTarget.NEW);

        Loan userNewLoan = new Loan();
        userNewLoan.setUser(userNew);
        userNewLoan.setCreationDate(LocalDate.now());
        userNewLoan.setTerm(12);
        userNewLoan.setAmount(10000);
        userNewLoan.setId(1L);

        //when
        when(userService.loadUserTargetProperties(targetNew.getId()))
                .thenReturn(newUserTargetDTO);

        //then
        assertThat(calculationService
                .calculateLoanInstallment(userNewLoan))
                .isEqualTo(234.03);
    }

    @Test
    void calculateLoanInstallmentForFrequentUser() {
        //given
        Target targetFrequent = new Target();
        targetFrequent.setId(2L);
        targetFrequent.setName("FREQUENT");

        User userFrequent = new User();
        userFrequent.setTarget(targetFrequent);
        userFrequent.setId(2L);

        UserTargetDTO frequentUserTargetDTO = new UserTargetDTO();
        frequentUserTargetDTO.setMaxAmount(1000000);
        frequentUserTargetDTO.setRate(0.10);
        frequentUserTargetDTO.setUserTarget(UserTarget.FREQUENT);

        Loan userNewLoan = new Loan();
        userNewLoan.setUser(userFrequent);
        userNewLoan.setCreationDate(LocalDate.now());
        userNewLoan.setTerm(12);
        userNewLoan.setAmount(10000);
        userNewLoan.setId(1L);

        //when
        when(userService.loadUserTargetProperties(targetFrequent.getId()))
                .thenReturn(frequentUserTargetDTO);

        //then
        assertThat(calculationService
                .calculateLoanInstallment(userNewLoan))
                .isEqualTo(159.4);
    }

    @Test
    void calculateLoanInstallmentForPremiumUser() {
        //given
        Target targetPremium = new Target();
        targetPremium.setId(3L);
        targetPremium.setName("PREMIUM");

        User userPremium = new User();
        userPremium.setTarget(targetPremium);
        userPremium.setId(3L);

        UserTargetDTO premiumUserTargetDTO = new UserTargetDTO();
        premiumUserTargetDTO.setMaxAmount(5000000);
        premiumUserTargetDTO.setRate(0.05);
        premiumUserTargetDTO.setUserTarget(UserTarget.PREMIUM);

        Loan userNewLoan = new Loan();
        userNewLoan.setUser(userPremium);
        userNewLoan.setCreationDate(LocalDate.now());
        userNewLoan.setTerm(12);
        userNewLoan.setAmount(10000);
        userNewLoan.setId(1L);

        //when
        when(userService.loadUserTargetProperties(targetPremium.getId()))
                .thenReturn(premiumUserTargetDTO);

        //then
        assertThat(calculationService
                .calculateLoanInstallment(userNewLoan))
                .isEqualTo(81.47);
    }
}