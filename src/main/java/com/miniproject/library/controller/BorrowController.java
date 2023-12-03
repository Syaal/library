package com.miniproject.library.controller;

import com.miniproject.library.dto.borrow.BorrowRequest;
import com.miniproject.library.dto.borrow.BorrowResponse;
import com.miniproject.library.entity.BorrowBook;
import com.miniproject.library.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowResponse> createBorrow(@Valid @RequestBody BorrowRequest borrowRequest) {
        BorrowResponse borrowResponse = borrowService.createBorrow(borrowRequest);
        return ResponseEntity.ok(borrowResponse);
    }

    @GetMapping
    public ResponseEntity<List<BorrowResponse>> getAllBorrow() {
        List<BorrowResponse> borrowResponses = borrowService.getAllBorrow();
        return ResponseEntity.ok(borrowResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowBook> getBorrowById(@PathVariable Integer id) {
        BorrowBook borrowResponse = borrowService.getBorrowById(id);
        return ResponseEntity.ok(borrowResponse);
    }
}

