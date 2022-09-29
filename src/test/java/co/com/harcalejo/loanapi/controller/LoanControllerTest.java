package co.com.harcalejo.loanapi.controller;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.service.LoanService;
import co.com.harcalejo.loanapi.util.ObjectToJson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
                .andExpect(MockMvcResultMatchers
                        .status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.loanId", Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.installment", Matchers.equalTo(351.54)));
    }
}
