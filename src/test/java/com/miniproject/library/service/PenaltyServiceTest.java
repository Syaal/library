package com.miniproject.library.service;

import com.miniproject.library.dto.penalty.PenaltyRequest;
import com.miniproject.library.dto.penalty.PenaltyResponse;
import com.miniproject.library.entity.Book;
import com.miniproject.library.entity.BookCart;
import com.miniproject.library.entity.Loan;
import com.miniproject.library.entity.Penalty;
import com.miniproject.library.repository.BookRepository;
import com.miniproject.library.repository.LoanRepository;
import com.miniproject.library.repository.PenaltyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PenaltyServiceTest {

    private PenaltyRepository penaltyRepository;
    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private LoanService loanService;
    private PenaltyService penaltyService;

    @BeforeEach
    void setUp() {
        penaltyRepository = mock(PenaltyRepository.class);
        loanRepository = mock(LoanRepository.class);
        bookRepository = mock(BookRepository.class);
        loanService = mock(LoanService.class);
        penaltyService = new PenaltyService(penaltyRepository, loanRepository, bookRepository, loanService);
    }

    @Test
    void testProcessPenaltyForLateReturnAndDamage_WhenLoanNotFound() {
        Integer loanId = 1;
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            penaltyService.processPenaltyForLateReturnAndDamage(new PenaltyRequest(), loanId, true, false);
        });
    }

    // Add more test cases for other scenarios, like overdue loan, damaged books, lost books, etc.

    @Test
    void testProcessPenaltyForLateReturnAndDamage_WhenLoanIsNotOverdue() {
        Integer loanId = 1;

        // Buat objek BookCart dengan daftar buku yang telah diinisialisasi
        List<Book> returnedBooks = new ArrayList<>();
        BookCart cart = new BookCart();
        cart.setId(1);
        cart.setBook(returnedBooks);

        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setDateBorrow(new Date());
        loan.setDateReturn(new Date());
        loan.setDueBorrow(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));
        loan.setBookCarts(cart); // Set BookCart to the Loan object

        // Stubbing untuk pemanggilan getBookCarts() pada objek Loan
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // Melakukan pengujian
        assertNull(penaltyService.processPenaltyForLateReturnAndDamage(new PenaltyRequest(), loanId, true, false));
    }

    @Test
    void testGetPenaltyById_WhenPenaltyFound() {
        Integer penaltyId = 1;
        Penalty penalty = new Penalty(); // Create a penalty object for testing
        penalty.setId(penaltyId);
        penalty.setCost(50);
        penalty.setLoan(new Loan()); // Set a loan object for the penalty
        when(penaltyRepository.findById(penaltyId)).thenReturn(Optional.of(penalty));

        PenaltyResponse penaltyResponse = penaltyService.getPenaltyById(penaltyId);

        assertNotNull(penaltyResponse);
        assertEquals(penalty.getId(), penaltyResponse.getId());
        assertEquals(penalty.getCost(), penaltyResponse.getCost());
        // Add more assertions based on the expected behavior of PenaltyResponse
    }
}
