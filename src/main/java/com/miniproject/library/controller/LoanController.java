package com.miniproject.library.controller;

import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.service.LoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@Tag(name = "Loan")
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/borrow")
    public ResponseEntity<LoanResponse> borrowBooks(@RequestBody BookCartRequest request){
        LoanResponse borrowResponse = loanService.borrowBooks(request);
        return new ResponseEntity<>(borrowResponse, HttpStatus.CREATED);
    }

    @PostMapping("/return")
    public ResponseEntity<LoanResponse> returnBooks(@RequestParam Integer loanId, @RequestParam boolean isDamagedOrLost) {
        LoanResponse response = loanService.returnBooks(loanId, isDamagedOrLost);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/anggota/{anggotaId}/loanId")
    public ResponseEntity<Integer> getLoanIdByAnggotaId(@PathVariable Integer anggotaId) {
        Integer loanId = loanService.getLoanIdByAnggotaId(anggotaId);

        if (loanId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan ID not found for the provided Anggota ID");
        }

        return ResponseEntity.ok(loanId);
    }

}
