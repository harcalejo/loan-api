package co.com.harcalejo.loanapi.controller;

import co.com.harcalejo.loanapi.dto.RequestLoanPayloadDTO;
import co.com.harcalejo.loanapi.dto.RequestLoanResponseDTO;
import co.com.harcalejo.loanapi.service.LoanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    @BeforeEach
    void setup() {
    }

    @Test
    public void shouldCreateLoanFoNewTargetUser() throws Exception{

        //given
        RequestLoanPayloadDTO requestLoanPayloadDTO =
                new RequestLoanPayloadDTO(
                        15021, 12, 1L);

        final String jsonPayload =
                objectToJson(requestLoanPayloadDTO);

        RequestLoanResponseDTO requestLoanResponseDTO =
                new RequestLoanResponseDTO(1L, 351.54);

        //when
        when(loanService.requestLoan(any()))
                .thenReturn(requestLoanResponseDTO);

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

    private String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();

        return objectWriter.writeValueAsString(object);
    }
}
