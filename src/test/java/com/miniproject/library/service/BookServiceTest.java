package com.miniproject.library.service;

import com.miniproject.library.dto.book.BookRequest;
import com.miniproject.library.dto.book.BookResponse;
import com.miniproject.library.entity.Book;
import com.miniproject.library.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BookServiceTest {
    @InjectMocks
    BookService bookService;
    @Mock
    BookRepository bookRepository;

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
//    void getAllBook() {
//        List<Book> expectedBooks = example();
//
//        when(bookRepository.findAll()).thenReturn(expectedBooks);
//
//        List<Book> result = bookService.getAllBook();
//
//        assertEquals(expectedBooks.size(), result.size());
//        verify(bookRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetBookByIdValid() {
//        Integer bookId = 1;
//        Book expectedBook = example().get(0);
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));
//
//        Book result = bookService.getBookByid(bookId);
//        Assertions.assertEquals(expectedBook.getId(), result.getId());
//        Assertions.assertEquals(expectedBook.getTitle(), result.getTitle());
//        Assertions.assertEquals(expectedBook.getAuthor(), result.getAuthor());
//        Assertions.assertEquals(expectedBook.getStock(), result.getStock());
//
//         verify(bookRepository, times(1)).findById(bookId);
//    }
//
//    @Test
//    void testGetBookByIdInvalid() {
//        Integer invalidBookId = 999;
//
//        when(bookRepository.findById(invalidBookId)).thenReturn(Optional.empty());
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> bookService.getBookByid(invalidBookId));
//
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//        assertEquals("Book not found", exception.getReason());
//
//         verify(bookRepository, times(1)).findById(invalidBookId);
//    }
//
//    @Test
//    void testCreateBook() {
//        BookRequest request = new BookRequest();
//        Date date = new Date(1999, Calendar.MARCH,12);
//        request.setTitle("Metamorphosis");
//        request.setAuthor("John");
//        request.setStock(999);
//        request.setPublisher("Pink Bat");
//        request.setPublicationDate(date);
//        //mock repository
//        Book savedBook = mapper.map(request, Book.class);
//        savedBook.setId(1);
//
//        when(bookRepository.save(any())).thenReturn(savedBook);
//        BookResponse expectedResponse = mapper.map(savedBook, BookResponse.class);
//
//        BookResponse actualResponse = bookService.addBook(request);
//        actualResponse.setId(1);
//
//        assertEquals(expectedResponse, actualResponse);
//
//        verify(bookRepository, times(1)).save(any());
//    }
//
//    @Test
//    void testUpdateBook() {
//        Book book = example().get(1);
//        BookRequest request = new BookRequest();
//        request.setTitle("Updated Title");
//        request.setAuthor("Updated Author");
//        request.setStock(100);
//        request.setPublisher("Updated Publisher");
//
//        Book existingBook = example().get(0);
//
//        // Mocking the behavior of the repository
//        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
//        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // When
//        BookResponse updatedBookResponse = bookService.updateBook(request, existingBook.getId());
//
//        // Then
//        assertEquals("Updated Title", existingBook.getTitle());
//        assertEquals("Updated Author", existingBook.getAuthor());
//        assertEquals(100, existingBook.getStock());
//        assertEquals("Updated Publisher", existingBook.getPublisher());
//        assertEquals(mapper.map(existingBook,BookResponse.class),updatedBookResponse);
//
//        // Verify that the repository methods were called
//        verify(bookRepository, times(1)).findById(existingBook.getId());
//        verify(bookRepository, times(1)).save(any(Book.class));
//    }
//
//    @Test
//    void testUpdateBookNotFound() {
//        Integer bookId = 1;
//        BookRequest request = new BookRequest();
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> bookService.updateBook(request, bookId));
//
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//        assertEquals("Book not found", exception.getReason());
//
//        verify(bookRepository, times(1)).findById(bookId);
//        verify(bookRepository, never()).save(any(Book.class));
//    }

}