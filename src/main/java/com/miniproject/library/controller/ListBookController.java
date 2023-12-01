package com.miniproject.library.controller;

import com.miniproject.library.dto.ListBook.ListBookResponse;
import com.miniproject.library.service.ListBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/list-books")
public class ListBookController {
    private final ListBookService listBookService;

    @GetMapping("/all")
    public ResponseEntity<List<ListBookResponse>>getAll(){
        List<ListBookResponse> responses = listBookService.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListBookResponse>getById(@PathVariable Integer id){
        ListBookResponse response = listBookService.getById(id);
        return ResponseEntity.ok(response);
    }
}
