package co.com.harcalejo.loanapi.controller;

import co.com.harcalejo.loanapi.dto.RequestLoanPayloadDTO;
import co.com.harcalejo.loanapi.dto.RequestLoanResponseDTO;
import co.com.harcalejo.loanapi.exception.UserException;
import co.com.harcalejo.loanapi.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<RequestLoanResponseDTO> requestLoan(
            @RequestBody RequestLoanPayloadDTO requestLoanPayloadDTO) {

        return new ResponseEntity<>(
                loanService
                        .requestLoan(requestLoanPayloadDTO), HttpStatus.CREATED);
    }

}
