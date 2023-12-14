package com.miniproject.library.controller;

import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoanResponse> returnBooks(@PathVariable Integer id,
                                                    @RequestParam(required = false, defaultValue = "false") boolean isDamaged,
                                                    @RequestParam(required = false, defaultValue = "false") boolean isLost) {
        LoanResponse loanResponse = loanService.returnBook(id, isDamaged, isLost);
        return new ResponseEntity<>(loanResponse, HttpStatus.OK);
    }
}
