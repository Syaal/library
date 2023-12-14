package com.miniproject.library.controller;

import com.miniproject.library.dto.book.BookRequest;
import com.miniproject.library.dto.book.BookResponse;
import com.miniproject.library.entity.Book;
import com.miniproject.library.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BookControllerTest {
    @InjectMocks
    BookController bookController;
    @Mock
    BookService bookService;
    private final ModelMapper mapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private List<Book> example(){
        List<Book> bookList = new ArrayList<>();
        Date date = new Date(1999, Calendar.MARCH,12);
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Metamorphosis");
        book1.setAuthor("John");
        book1.setStock(999);
        book1.setPublisher("Pink Bat");
        book1.setPublicationDate(date);
        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Oyasumi pupun");
        book2.setAuthor("Inio Asano");
        book2.setPublisher("VIZ Media");
        book2.setStock(6868);
        book2.setPublicationDate(date);
        bookList.add(book1);
        bookList.add(book2);
        return bookList;
    }

//    @Test
//    void createBook() {
//        BookRequest request = new BookRequest();
//        Date date = new Date(1999, Calendar.MARCH,12);
//        request.setTitle("Metamorphosis");
//        request.setAuthor("John");
//        request.setStock(6969);
//        request.setPublisher("Pink");
//        request.setPublicationDate(date);
//
//        Book book = mapper.map(request, Book.class);
//        BookResponse expectedResponse = mapper.map(book, BookResponse.class);
//
//        when(bookService.addBook(request)).thenReturn(expectedResponse);
//        ResponseEntity<BookResponse> responseEntity = bookController.addBook(request);
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assertions.assertEquals(expectedResponse, responseEntity.getBody());
//    }
//
//    @Test
//    void getAllBook() {
//
//        List<Book> expectedBookResponses = example();
//
//        when(bookService.getAllBook()).thenReturn(expectedBookResponses);
//
//        ResponseEntity<List<Book>> responseEntity = bookController.getAllBook();
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(expectedBookResponses, responseEntity.getBody());
//
//        verify(bookService, times(1)).getAllBook();
//    }
//
//    @Test
//     void testGetBookById() {
//        Book book = example().get(1);
//
//        // Mock the behavior of the bookService
//        when(bookService.getBookByid(book.getId())).thenReturn(book);
//
//        ResponseEntity<Book> responseEntity = bookController.getBookById(2);
//
//        Assertions.assertEquals(2,book.getId());
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assertions.assertEquals(book, responseEntity.getBody());
//    }
//
//    @Test
//    void updateBook() {
//        Book book = example().get(1);
//        BookRequest request = new BookRequest();
//        request.setTitle("Metamorphosis");
//        request.setAuthor("John");
//        request.setStock(6969);
//        request.setPublisher("Pink");
//        book.setTitle(request.getTitle());
//        book.setAuthor(request.getAuthor());
//        book.setStock(request.getStock());
//        book.setPublisher(request.getPublisher());
//
//        BookResponse expectedBookResponse = mapper.map(book, BookResponse.class);
//
//        when(bookService.updateBook(request, 2)).thenReturn(expectedBookResponse);
//        ResponseEntity<BookResponse> responseEntity = bookController.updateBook(2, request);
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assertions.assertEquals(expectedBookResponse, responseEntity.getBody());
//
//    }
//
//    @Test
//    void testWithBlankRequest() {
//        BookRequest blankRequest = new BookRequest();
//        blankRequest.setTitle("");
//        blankRequest.setAuthor("");
//        // Set other fields as needed
//
//        // Mock the behavior of bookService.createBook to throw an exception
//        when(bookService.addBook(blankRequest)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Masukkan Judul"));
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> bookController.addBook(blankRequest));
//        // Act and Assert
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
//        assertEquals("Masukkan Judul", exception.getReason());
//    }
}