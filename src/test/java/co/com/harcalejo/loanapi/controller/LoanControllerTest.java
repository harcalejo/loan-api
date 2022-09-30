package co.com.harcalejo.loanapi.controller;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.dto.LoanByRangeResponseDTO;
import co.com.harcalejo.loanapi.dto.UserTargetDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.entity.Target;
import co.com.harcalejo.loanapi.entity.User;
import co.com.harcalejo.loanapi.entity.UserTarget;
import co.com.harcalejo.loanapi.service.LoanService;
import co.com.harcalejo.loanapi.util.ObjectToJson;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoanService loanService;

    private ObjectToJson objectToJson;

    @BeforeEach
    void setup() {
        objectToJson = new ObjectToJson();
    }

    @Test
    public void shouldCreateLoanForUser() throws Exception{

        //given
        CreateLoanRequestDTO createLoanRequestDTO =
                new CreateLoanRequestDTO(
                        15021, 12, 1L);

        final String jsonPayload =
                objectToJson.transform(createLoanRequestDTO);

        CreateLoanResponseDTO createLoanResponseDTO =
                new CreateLoanResponseDTO(1L, 351.54);

        //when
        when(loanService.createLoan(any()))
                .thenReturn(createLoanResponseDTO);

        //then
        this.mockMvc
                .perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.loanId", Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.installment", Matchers.equalTo(351.54)));
    }

    @Test
    public void shouldReturnLoansByDateBetween() throws Exception {
        //given
        final LocalDate endDate = LocalDate.now();
        final LocalDate startDate = endDate.minusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        final int page = 0;
        final int size = 2;
        Pageable paging = PageRequest.of(page, size);

        Target targetNew = new Target();
        targetNew.setId(1L);
        targetNew.setName(UserTarget.NEW.getValue());

        User userNew = new User();
        userNew.setTarget(targetNew);
        userNew.setId(1L);

        UserTargetDTO newUserTargetDTO = new UserTargetDTO();
        newUserTargetDTO.setMaxAmount(500000);
        newUserTargetDTO.setRate(0.15);
        newUserTargetDTO.setUserTarget(UserTarget.NEW);

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

        //when
        when(loanService
                .getLoansByRangeOfTime(startDate, endDate, paging))
                .thenReturn(loanPage);

        //then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/loans")
                        .param("startDate",
                                startDate.format(formatter))
                        .param("endDate",
                                endDate.format(formatter))
                        .param("page", "0")
                        .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(2));
    }
}
