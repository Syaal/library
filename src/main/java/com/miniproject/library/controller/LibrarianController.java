package com.miniproject.library.controller;

import com.miniproject.library.dto.librarian.LibrarianRequest;
import com.miniproject.library.dto.librarian.LibrarianResponse;
import com.miniproject.library.entity.Librarian;
import com.miniproject.library.service.LibrarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarian")
@RequiredArgsConstructor
public class LibrarianController {

    private final LibrarianService librarianService;

    @PostMapping()
    public ResponseEntity<LibrarianResponse> createLibrarian(@RequestBody LibrarianRequest request){
        LibrarianResponse response = librarianService.createLibrarian(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Librarian> getLibrarianById(@RequestParam("id") Integer id){
        Librarian response = librarianService.getByIdLibrarian(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Librarian>> getAllLibrarian(){
        List<Librarian> response = librarianService.getAllLibrarian();
        return ResponseEntity.ok(response);
    }
}
