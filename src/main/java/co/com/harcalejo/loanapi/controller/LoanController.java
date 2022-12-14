package co.com.harcalejo.loanapi.controller;

import co.com.harcalejo.loanapi.dto.CreateLoanRequestDTO;
import co.com.harcalejo.loanapi.dto.CreateLoanResponseDTO;
import co.com.harcalejo.loanapi.dto.LoanByIdResponseDTO;
import co.com.harcalejo.loanapi.dto.LoanByRangeResponseDTO;
import co.com.harcalejo.loanapi.entity.Loan;
import co.com.harcalejo.loanapi.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    /**
     * Constante para definir el numero de pagina por defecto en una consulta
     * usando paginacion
     */
    private static final String DEFAULT_PAGE = "0";

    /**
     * Constante para definir el tamaño de la lista por defecto en una consulta
     * usando paginacion
     */
    private static final String DEFAULT_SIZE = "10";
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

    @GetMapping
    public ResponseEntity<Page<LoanByRangeResponseDTO>> getLoansByRangeOfTime(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<LoanByRangeResponseDTO> responseDTO = loanService
                .getLoansByRangeOfTime(from,
                        to, paging).map(LoanByRangeResponseDTO::new);

        return new ResponseEntity<>(
                responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<LoanByIdResponseDTO> getLoanById(@PathVariable Long id) {
        return new ResponseEntity<>(
                    new LoanByIdResponseDTO(loanService.getLoanById(id)),
                HttpStatus.OK);
    }

}
