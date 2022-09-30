package co.com.harcalejo.loanapi.service;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.Target;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.entity.UserTarget;
import co.com.harcalejo.loanapi.exception.LoanException;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
    private User userNew;
    private UserTargetDTO newUserTargetDTO;

    @BeforeEach
    void setUp() {
        loanService = new LoanServiceImpl(
                loanRepository, userService, calculationService);

        Target targetNew = new Target();
        targetNew.setId(1L);
        targetNew.setName(UserTarget.NEW.getValue());

        userNew = new User();
        userNew.setTarget(targetNew);
        userNew.setId(1L);

        newUserTargetDTO = new UserTargetDTO();
        newUserTargetDTO.setMaxAmount(500000);
        newUserTargetDTO.setRate(0.15);
        newUserTargetDTO.setUserTarget(UserTarget.NEW);
    }

    @Test
    void shouldCreateLoanForUserWithTargetAsNew() {

        //given
        CreateLoanRequestDTO createLoanRequestDTO = new CreateLoanRequestDTO(
                10000.0, 12, 1L);

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

    @Test
    void shouldFailCreateLoanWithAmountGreaterThanMaxAmount() {
        //given
        CreateLoanRequestDTO createLoanRequestDTO = new CreateLoanRequestDTO(
                500001.0, 12, 1L);

        UserTargetDTO newUserTargetDTO = new UserTargetDTO();
        newUserTargetDTO.setMaxAmount(500000);
        newUserTargetDTO.setRate(0.15);
        newUserTargetDTO.setUserTarget(UserTarget.NEW);

        //when
        when(userService.getUser(createLoanRequestDTO.getUserId()))
                .thenReturn(userNew);
        when(userService.loadUserTargetProperties(userNew.getTarget().getId()))
                .thenReturn(newUserTargetDTO);

        assertThatExceptionOfType(UserException.class)
                .isThrownBy(() -> loanService
                        .createLoan(createLoanRequestDTO))
                .withMessage("El usuario supera el monto permitido");
    }

    @Test
    void shouldReturnLoansWithDateBetweenStarDateAndEndDate() {
        //given
        int page = 0;
        int size = 2;

        LocalDate endDate =
                LocalDate.now();

        LocalDate startDate =
                endDate.minusYears(1);

        Loan loan_a = new Loan();
        loan_a.setUser(userNew);
        loan_a.setCreationDate(
                LocalDate.now().minusMonths(4));
        loan_a.setTerm(12);
        loan_a.setAmount(300000.0);
        loan_a.setId(1L);

        Loan loan_b = new Loan();
        loan_b.setUser(userNew);
        loan_b.setCreationDate(
                LocalDate.now()
                        .minusMonths(8));
        loan_b.setTerm(24);
        loan_b.setAmount(10000.0);
        loan_b.setId(2L);

        List<Loan> loanList = new ArrayList<>();
        loanList.add(loan_a);
        loanList.add(loan_b);

        Page<Loan> loanPage = new PageImpl<>(loanList);
        Pageable paging = PageRequest.of(page, size);

        //when
        when(loanService.getLoansByRangeOfTime(
                startDate,
                endDate,
                PageRequest.of(page,
                        size)))
                .thenReturn(loanPage);

        //then
        assertThat(loanService
                .getLoansByRangeOfTime(startDate, endDate, paging)
                .getSize())
            .isEqualTo(size);
        assertThat(loanService
                .getLoansByRangeOfTime(startDate, endDate, paging)
                .getContent().get(0).getCreationDate())
            .isBetween(startDate, endDate);
        assertThat(loanService
                .getLoansByRangeOfTime(
                        startDate, endDate, paging)
                .getContent().get(1).getCreationDate())
            .isBetween(startDate, endDate);
    }

    @Test
    void shouldReturnEmptySubList() {
        //given
        int page = 0;
        int size = 2;

        LocalDate endDate =
                LocalDate.now();

        LocalDate startDate =
                endDate.minusWeeks(1);

        List<Loan> loanList = new ArrayList<>();

        Page<Loan> loanPage = new PageImpl<>(loanList);
        Pageable paging = PageRequest.of(page, size);

        //when
        when(loanService.getLoansByRangeOfTime(
                startDate,
                endDate,
                PageRequest.of(page,
                        size)))
                .thenReturn(loanPage);

        //then
        assertThat(loanService
                .getLoansByRangeOfTime(startDate, endDate, paging))
            .isEmpty();
    }

    @Test
    void shouldReturnLoanById() {
        //given
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setUser(userNew);
        loan.setCreationDate(
                LocalDate.now().minusMonths(4));
        loan.setTerm(12);
        loan.setAmount(300000.0);
        loan.setId(loanId);
        Optional<Loan> optionalLoan =
                Optional.of(loan);

        //when
        when(loanRepository.findById(loanId))
                .thenReturn(optionalLoan);

        //then
        assertThat(loanService.getLoanById(loanId))
                .isEqualTo(loan);
    }

    @Test
    void shouldFailLoanIdDoesNotExist() {
        //given
        Long loanId = 560L;
        Optional<Loan> emptyOptional = Optional.empty();

        //when
        when(loanRepository.findById(loanId))
                .thenReturn(emptyOptional);

        //then
        assertThatExceptionOfType(LoanException.class)
                .isThrownBy(() -> loanService
                        .getLoanById(loanId))
                .withMessage("No existe un prestamo con el identificador: " + loanId);
    }
}