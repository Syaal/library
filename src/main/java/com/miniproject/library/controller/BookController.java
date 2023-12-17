package com.miniproject.library.controller;

import com.lowagie.text.*;
import com.miniproject.library.dto.book.BookRequest;
import com.miniproject.library.dto.book.BookResponse;
import com.miniproject.library.entity.Book;
import com.miniproject.library.service.BookReport;
import com.miniproject.library.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest request){
        BookResponse bookResponse = bookService.addBook(request);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Integer id, @Valid
    @RequestBody BookRequest request){
        BookResponse bookResponse = bookService.updateBook(request, id);
        return ResponseEntity.ok(bookResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBook(){
        List<Book> books = bookService.getAllBook();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id){
        Book book = bookService.getBookByIdBook(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/book-report")
    public void bookReport(HttpServletResponse response) throws DocumentException, IOException {
        List<Book> bookList = bookService.getAllBook();
        BookReport bookPdf = new BookReport();
        bookPdf.bookReport(bookList, response);
    }
}
