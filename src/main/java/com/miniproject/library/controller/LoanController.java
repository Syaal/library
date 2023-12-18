package com.miniproject.library.controller;

import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/borrow")
    public ResponseEntity<LoanResponse> borrowBooks(@RequestBody BookCartRequest request){
        LoanResponse borrowResponse = loanService.borrowBooks(request);
        return new ResponseEntity<>(borrowResponse, HttpStatus.CREATED);
    }

    @PostMapping("/return/{loanId}")
    public ResponseEntity<LoanResponse> returnBooks(
            @PathVariable Integer loanId,
            @RequestParam List<Integer> bookIdsReturned,
            @RequestParam boolean isDamagedOrLost
    ) {
        try {
            LoanResponse response = loanService.returnBooks(loanId, bookIdsReturned, isDamagedOrLost);
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException | ResponseStatusException e) {
            return ResponseEntity.badRequest().build();
        }
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
