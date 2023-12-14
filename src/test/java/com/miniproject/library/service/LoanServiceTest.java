package com.miniproject.library.service;

import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.dto.penalty.PenaltyRequest;
import com.miniproject.library.dto.penalty.PenaltyResponse;
import com.miniproject.library.entity.*;
import com.miniproject.library.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private AnggotaRepository anggotaRepository;

    @Mock
    private LibrarianRepository librarianRepository;

    @Mock
    private BookCartRepository bookCartRepository;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    PenaltyService penaltyService;

    @Mock
    PenaltyRepository penaltyRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void testBorrowBooks() {
        MockitoAnnotations.initMocks(this);
        Book book1 = new Book();
        book1.setId(1);
        book1.setStock(1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setStock(2);

        Anggota anggota = new Anggota();
        anggota.setId(1);

        // Mock data
        BookCartRequest bookCartRequest = new BookCartRequest();
        bookCartRequest.setAnggotaId(anggota.getId());
        bookCartRequest.setBookIds(List.of(book1.getId(), book2.getId()));

        List<Book> books = List.of(book1, book2);

        BookCart bookCart = new BookCart();
        bookCart.setId(1);
        bookCart.setAnggota(anggota);
        bookCart.setBook(books);

        when(anggotaRepository.findById(1)).thenReturn(java.util.Optional.of(anggota));
        when(bookRepository.findAllById(List.of(1, 2))).thenReturn(books);
        when(bookCartRepository.save(any(BookCart.class))).thenReturn(bookCart);
        when(bookCartRepository.findById(any())).thenReturn(java.util.Optional.of(bookCart));

        Loan loan = new Loan();
        loan.setId(1);
        loan.setDateBorrow(new Date(2023-12-13));
        loan.setDueBorrow(new Date(2023-12-20));
        loan.setBookCarts(bookCart);
        // Test borrowBooks method
        LoanResponse loanResponse = loanService.borrowBooks(bookCartRequest);

        // Assertions
        assertNotNull(loanResponse);
        assertEquals(1, loanResponse.getBookCartId());
        assertNotNull(loan.getId());
        assertNotNull(loan.getDateBorrow());
        assertNotNull(loan.getDueBorrow());

        // Verify that the methods were called with the expected parameters
        verify(anggotaRepository).findById(1);
        verify(bookRepository).findAllById(List.of(1, 2));
        verify(bookCartRepository).save(any(BookCart.class));
        verify(bookRepository, times(2)).save(any(Book.class));
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void testReturnBook() {
        MockitoAnnotations.initMocks(this);

        Book book1 = new Book();
        book1.setId(1);
        book1.setStock(1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setStock(2);

        Anggota anggota = new Anggota();
        anggota.setId(1);

        // Mock data
        BookCartRequest bookCartRequest = new BookCartRequest();
        bookCartRequest.setAnggotaId(anggota.getId());
        bookCartRequest.setBookIds(List.of(book1.getId(), book2.getId()));

        List<Book> books = List.of(book1, book2);

        BookCart bookCart = new BookCart();
        bookCart.setId(1);
        bookCart.setAnggota(anggota);
        bookCart.setBook(books);

        List<Book> returnedBooks = new ArrayList<>();
        returnedBooks.add(book1);
        returnedBooks.add(book2);

        Loan loan = new Loan();
        loan.setId(1);
        loan.setDateBorrow(new Date(2023-12-13));
        loan.setDueBorrow(new Date(2023-12-20));
        loan.setBookCarts(bookCart);

        Librarian librarian = new Librarian();
        librarian.setId(1);
        librarian.setName("Metamorphosis");


        when(librarianRepository.findById(1)).thenReturn(java.util.Optional.of(librarian));
        when(loanRepository.findById(1)).thenReturn(java.util.Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(bookCartRepository.findById(1)).thenReturn(java.util.Optional.of(bookCart));

        // Test returnBook method
        LoanResponse loanResponse = loanService.returnBook(1, 1);

        // Assertions
        assertNotNull(loanResponse);
        assertEquals(1, loanResponse.getBookCartId());
        assertNotNull(loanResponse.getId());
        assertNotNull(loanResponse.getDateBorrow());
        assertNotNull(loanResponse.getDueBorrow());
        assertNotNull(loanResponse.getDateReturn());

        // Verify that the methods were called with the expected parameters
        verify(librarianRepository).findById(1);
        verify(loanRepository).findById(1);
        verify(loanRepository).save(any(Loan.class));
    }


    @Test
    void testBorrowBooksOutOfStock() {
        MockitoAnnotations.initMocks(this);

        // Mock data
        BookCartRequest bookCartRequest = new BookCartRequest();
        bookCartRequest.setAnggotaId(1);
        bookCartRequest.setBookIds(List.of(1, 2));

        Anggota anggota = new Anggota();
        anggota.setId(1);

        Book book1 = new Book();
        book1.setId(1);
        book1.setStock(0);  // Out of stock

        Book book2 = new Book();
        book2.setId(2);
        book2.setStock(0);  // Out of stock

        List<Book> books = List.of(book1, book2);

        when(anggotaRepository.findById(1)).thenReturn(java.util.Optional.of(anggota));
        when(bookRepository.findAllById(List.of(1, 2))).thenReturn(books);

        // Test borrowBooks method
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.borrowBooks(bookCartRequest);
        });

        // Assertions
        assertEquals("Books Out of Stock", exception.getMessage());

        // Verify that the methods were called with the expected parameters
        verify(anggotaRepository).findById(1);
        verify(bookRepository).findAllById(List.of(1, 2));
    }

    @Test
    void testReturnBookAlreadyReturned() {
        MockitoAnnotations.initMocks(this);

        // Mock data
        Librarian librarian = new Librarian();
        librarian.setId(1);

        Loan loan = new Loan();
        loan.setId(1);
        loan.setDateReturn(new Date());

        when(librarianRepository.findById(1)).thenReturn(java.util.Optional.of(librarian));
        when(loanRepository.findById(1)).thenReturn(java.util.Optional.of(loan));

        // Test returnBook method
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.returnBook(1, 1);
        });

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Loan with ID 1 has already been returned", exception.getReason());

        // Verify that the methods were called with the expected parameters
        verify(librarianRepository).findById(1);
        verify(loanRepository).findById(1);
        verifyNoMoreInteractions(bookRepository, loanRepository);
    }


    @Test
    void testReturnBookSuccessfully() {
        MockitoAnnotations.initMocks(this);

        // Mock data
        Librarian librarian = new Librarian();
        librarian.setId(1);

        BookCart bookCart = new BookCart();
        bookCart.setId(1);
        Loan loan = new Loan();
        loan.setId(1);
        loan.setBookCarts(bookCart);


        when(librarianRepository.findById(1)).thenReturn(java.util.Optional.of(librarian));
        when(loanRepository.findById(1)).thenReturn(java.util.Optional.of(loan));
        when(bookCartRepository.findById(1)).thenReturn(java.util.Optional.of(bookCart));

        // Test returnBook method
        LoanResponse loanResponse = loanService.returnBook(1,1);
        loanResponse.setId(1);
        loanResponse.setDateBorrow(new Date(2023-12-20));


        // Assertions
        assertNotNull(loanResponse);
        assertNotNull(loanResponse.getDateReturn());
        assertEquals(1, loanResponse.getId());


        verify(librarianRepository).findById(1);
        verify(loanRepository).findById(1);
        verify(loanRepository).save(any(Loan.class));
        verifyNoMoreInteractions(librarianRepository, loanRepository, bookCartRepository, bookRepository);
    }
}

