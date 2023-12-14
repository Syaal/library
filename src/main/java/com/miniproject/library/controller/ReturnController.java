package com.miniproject.library.controller;

import com.miniproject.library.dto.returnbook.ReturnRequest;
import com.miniproject.library.dto.returnbook.ReturnResponse;
import com.miniproject.library.service.ReturnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/return")
@RequiredArgsConstructor
public class ReturnController {

    private final ReturnService returnService;

    @PostMapping
    public ResponseEntity<ReturnResponse> createReturnBook(@Valid @RequestBody ReturnRequest returnRequest) {
        ReturnResponse returnResponse = returnService.createReturnBook(returnRequest);
        return ResponseEntity.ok(returnResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReturnResponse>> getAllReturn() {
        List<ReturnResponse> returnResponses = returnService.getAllReturnBook();
        return ResponseEntity.ok(returnResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnResponse> getReturnBookById(@PathVariable Integer id) {
        ReturnResponse returnResponse = returnService.getReturnBookById(id);
        return ResponseEntity.ok(returnResponse);
    }

}
