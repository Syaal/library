package com.miniproject.library.service;

import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.entity.Anggota;
import com.miniproject.library.entity.Book;
import com.miniproject.library.entity.BookCart;
import com.miniproject.library.entity.Loan;
import com.miniproject.library.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private AnggotaRepository anggotaRepository;

    @Mock
    private BookCartRepository bookCartRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PenaltyService penaltyService;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBorrowBooksWithAvailableBooks() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setStock(1);

        Book book2 = new Book();
        book2.setId(2);
        book2.setStock(2);

        Anggota anggota = new Anggota();
        anggota.setId(1);

        BookCartRequest bookCartRequest = new BookCartRequest();
        bookCartRequest.setAnggotaId(anggota.getId());
        bookCartRequest.setBookIds(List.of(book1.getId(), book2.getId()));

        List<Book> books = List.of(book1, book2);

        BookCart bookCart = new BookCart();
        bookCart.setId(1);
        bookCart.setAnggota(anggota);
        bookCart.setBook(books);

        when(anggotaRepository.findById(1)).thenReturn(Optional.of(anggota));
        when(bookRepository.findAllById(List.of(1, 2))).thenReturn(books);
        when(bookCartRepository.save(any(BookCart.class))).thenReturn(bookCart);
        when(bookCartRepository.findById(any())).thenReturn(Optional.of(bookCart));

        Loan loan = new Loan();
        loan.setId(1);
        loan.setDateBorrow(new Date());
        loan.setDueBorrow(new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000))); // Tambah 7 hari dari waktu peminjaman
        loan.setBookCarts(bookCart);

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanResponse loanResponse = loanService.borrowBooks(bookCartRequest);


        assertNotNull(loanResponse);
        assertEquals(1, loanResponse.getBookCartId());
        assertNotNull(loan.getId());
        assertNotNull(loan.getDateBorrow());
        assertNotNull(loan.getDueBorrow());

        verify(anggotaRepository).findById(1);
        verify(bookRepository).findAllById(List.of(1, 2));
        verify(bookCartRepository).save(any(BookCart.class));
        verify(bookRepository, times(2)).save(any(Book.class));
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    public void testBorrowBooksWithUnavailableBooks() {
        BookCartRequest bookCartRequest = new BookCartRequest();
        bookCartRequest.setAnggotaId(1);
        bookCartRequest.setBookIds(Arrays.asList(1, 2, 3));

        List<Book> unavailableBooks = Collections.emptyList();

        when(anggotaRepository.findById(1)).thenReturn(Optional.of(new Anggota()));
        when(bookRepository.findAllById(Arrays.asList(1, 2, 3))).thenReturn(unavailableBooks);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.borrowBooks(bookCartRequest);
        });

        assertEquals("Books Out of Stock", exception.getMessage());
    }

    @Test
    void testBorrowBooks_AnggotaNotFound() {
        BookCartRequest request = new BookCartRequest();
        request.setAnggotaId(999); // ID yang tidak ada

        when(anggotaRepository.findById(999)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.borrowBooks(request);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id Anggota It's Not Exist!!!", exception.getReason());
    }


    @Test
    void testReturnBooks() {
        int loanId = 1;
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setDateBorrow(new Date());
        loan.setDueBorrow(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)));

        BookCart bookCart = new BookCart();
        List<Book> books = new ArrayList<>();
        bookCart.setBook(books);
        loan.setBookCarts(bookCart);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        LoanResponse loanResponse = loanService.returnBooks(loanId, false);

        verify(penaltyService, never()).createPenalty(any(Loan.class), anyInt());
        verify(loanRepository, times(1)).save(any(Loan.class));
        assertNotNull(loanResponse);
        assertEquals(loanId, loanResponse.getId());
        assertEquals(loan.getDateBorrow(), loanResponse.getDateBorrow());
        assertEquals(loan.getDueBorrow(), loanResponse.getDueBorrow());
        assertEquals(bookCart.getId(), loanResponse.getBookCartId());
    }

    @Test
    void testReturnBooks_WhenLoanNotFound() {
        int loanId = 2;
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> loanService.returnBooks(loanId, false));
        verify(penaltyService, never()).createPenalty(any(Loan.class), anyInt());
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void testReturnBooks_WhenOverdueAndNotDamaged() {
        int loanId = 1;
        Loan loan = new Loan();
        loan.setId(loanId);

        Date currentDate = new Date();
        Date dueDate = new Date(currentDate.getTime() - TimeUnit.DAYS.toMillis(1)); // Tanggal jatuh tempo sudah lewat
        loan.setDateBorrow(new Date(currentDate.getTime() - TimeUnit.DAYS.toMillis(5))); // Dipinjam 5 hari yang lalu
        loan.setDueBorrow(dueDate);

        BookCart bookCart = new BookCart();
        bookCart.setBook(new ArrayList<>());
        loan.setBookCarts(bookCart);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        LoanResponse loanResponse = loanService.returnBooks(loanId, false);

        verify(penaltyService, times(1)).createPenalty(eq(loan), anyInt());
        verify(loanRepository, times(1)).save(any(Loan.class));
        assertNotNull(loanResponse);
        assertEquals(loanId, loanResponse.getId());
        assertEquals(loan.getDateBorrow(), loanResponse.getDateBorrow());
        assertEquals(loan.getDueBorrow(), loanResponse.getDueBorrow());
        assertEquals(bookCart.getId(), loanResponse.getBookCartId());
    }

    @Test
    void testReturnBooksWithLateAndDamagedBooks() {
        int loanId = 2;
        Loan loan = new Loan();
        loan.setId(loanId);

        Date currentDate = new Date();
        Date dueDate = new Date(currentDate.getTime() - TimeUnit.DAYS.toMillis(1)); // Tanggal jatuh tempo sudah lewat
        loan.setDateBorrow(new Date(currentDate.getTime() - TimeUnit.DAYS.toMillis(5))); // Dipinjam 5 hari yang lalu
        loan.setDueBorrow(dueDate);

        BookCart bookCart = new BookCart();
        bookCart.setBook(new ArrayList<>());
        loan.setBookCarts(bookCart);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        LoanResponse loanResponse = loanService.returnBooks(loanId, true);

        verify(penaltyService, times(2)).createPenalty(eq(loan), anyInt());
        verify(loanRepository, times(1)).save(any(Loan.class));
        assertNotNull(loanResponse);
        assertEquals(loanId, loanResponse.getId());
        assertEquals(loan.getDateBorrow(), loanResponse.getDateBorrow());
        assertEquals(loan.getDueBorrow(), loanResponse.getDueBorrow());
        assertEquals(bookCart.getId(), loanResponse.getBookCartId());
    }


    @Test
    void testReturnBooks_DamagedOrLost() {
        Loan loan = new Loan();
        loan.setId(1);
        loan.setDateBorrow(new Date());
        loan.setDueBorrow(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5))); // overdue loan
        BookCart bookCart = new BookCart();
        List<Book> books = new ArrayList<>();
        Book damagedBook = new Book();
        damagedBook.setId(1);
        books.add(damagedBook);
        bookCart.setBook(books);
        loan.setBookCarts(bookCart);

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        // Eksekusi
        LoanResponse response = loanService.returnBooks(1, true);

        // Verifikasi
        verify(penaltyService, times(1)).createPenalty(eq(loan), eq(5000000));
        assertEquals(1, response.getId());
    }
}
