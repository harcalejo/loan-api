package co.com.harcalejo.loanapi.controller;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
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
    public ResponseEntity<CreateLoanResponseDTO> createLoan(
            @RequestBody CreateLoanRequestDTO createLoanRequestDTO) {

        return new ResponseEntity<>(
                loanService
                        .createLoan(createLoanRequestDTO), HttpStatus.CREATED);
    }

}
