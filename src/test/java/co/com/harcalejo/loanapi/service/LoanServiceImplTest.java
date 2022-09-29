package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.Target;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.entity.UserTarget;
import co.com.harcalejo.loanapi.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoanServiceImplTest {

    @MockBean
    LoanRepository loanRepository;
    @MockBean
    UserService userService;
    @MockBean
    CalculationService calculationService;
    LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanServiceImpl(
                loanRepository, userService, calculationService);
    }

    @Test
    void shouldCreateLoanForUserWithTargetAsNew() {

        //given
        CreateLoanRequestDTO createLoanRequestDTO = new CreateLoanRequestDTO(
                10000.0, 12, 1L);

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

        CreateLoanResponseDTO createLoanResponseDTO = new CreateLoanResponseDTO();
        createLoanResponseDTO.setLoanId(1L);
        createLoanResponseDTO.setInstallment(234.03);

        Loan userNewLoan = new Loan();
        userNewLoan.setUser(userNew);
        userNewLoan.setCreationDate(LocalDate.now());
        userNewLoan.setInstallment(234.03);
        userNewLoan.setTerm(createLoanRequestDTO.getTerm());
        userNewLoan.setAmount(createLoanRequestDTO.getAmount());
        userNewLoan.setId(1L);

        //when
        when(userService.getUser(createLoanRequestDTO.getUserId()))
                .thenReturn(userNew);
        when(userService.loadUserTargetProperties(userNew.getTarget().getId()))
                .thenReturn(newUserTargetDTO);
        when(calculationService.calculateLoanInstallment(any()))
                .thenReturn(234.03);
        when(loanRepository.save(any()))
                .thenReturn(userNewLoan);

        //then
        assertThat(loanService.createLoan(createLoanRequestDTO)).isEqualTo(createLoanResponseDTO);
    }
}