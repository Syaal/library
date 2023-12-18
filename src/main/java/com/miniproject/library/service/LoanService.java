package com.miniproject.library.service;

import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.entity.*;
import com.miniproject.library.exception.ResourceNotFoundException;
import com.miniproject.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final AnggotaRepository anggotaRepository;
    private final BookCartRepository bookCartRepository;
    private final BookRepository bookRepository;
    private final PenaltyService penaltyService;

    private static final String ID_ANGGOTA_NOT_FOUND = "Id Anggota Not Found";
    private static final String BOOK_OUT_OF_STOCK = "Book Out of Stock";
    private static final String ID_LOAN_NOT_FOUND = "Id Loan Not Found";

    public LoanResponse borrowBooks(BookCartRequest bookCartRequest){

        Anggota anggota = anggotaRepository.findById(bookCartRequest.getAnggotaId()).orElseThrow(() ->
                    new ResourceNotFoundException(ID_ANGGOTA_NOT_FOUND));
        List<Book> books = bookRepository.findAllById(bookCartRequest.getBookIds());
        List<Book> availableBooks = getAvailableBook(books);

        if (availableBooks.isEmpty()){
            increaseWishList(books);
            throw new ResourceNotFoundException(BOOK_OUT_OF_STOCK);
        }

        //create BookCart
        BookCart bookCart = new BookCart();
        bookCart.setAnggota(anggota);
        bookCart.setBook(availableBooks);
        bookCartRepository.save(bookCart);

        //create Loan
        Loan loan = new Loan();
        loan.setDateBorrow(new Date());
        loan.setDueBorrow(calculateDueDate());
        loan.setBookCarts(bookCart);
        loanRepository.save(loan);

        //update data BookStock dan Jumlah Baca
        updateBookStockAndRead(availableBooks, true);

        return LoanResponse.builder()
                .id(loan.getId())
                .bookCartId(loan.getBookCarts().getId())
                .dateBorrow(loan.getDateBorrow())
                .dueBorrow(loan.getDueBorrow())
                .build();
    }

    private List<Book> getAvailableBook(List<Book> booksToBorrow){
        return booksToBorrow.stream()
                .filter(book -> book.getStock() > 0)
                .toList();
    }

    private void increaseWishList(List<Book> books){
        for (Book book : books){
            Integer currentWishlist = book.getWishlist();
            book.setWishlist(currentWishlist != null ? book.getWishlist() + 1 : 1);
            bookRepository.save(book);
        }
    }

    private void updateBookStockAndRead(List<Book> books, boolean increaseRead){
        for (Book book : books){
            if (increaseRead){
                //kurang stock
                book.setStock(book.getStock() - 1);
                //tambah jumlah baca
                Integer currentRead = book.getRead();
                book.setRead(currentRead != null ? book.getRead() + 1 : 1);
            } else {
                //tambah stock
                Integer currentStock = book.getStock();
                book.setStock(currentStock != null ? book.getStock() + 1 : 1);
            }
            bookRepository.save(book);
        }
    }

    private Date calculateDueDate(){
        // 7 hari dari waktu peminjaman
        return new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));
    }

    public LoanResponse returnBooks(Integer loanId, boolean isDamagedOrLost) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException(ID_LOAN_NOT_FOUND));

        Date currentDate = new Date();

        if (currentDate.after(loan.getDueBorrow())) {
            long diffInMillies = Math.abs(currentDate.getTime() - loan.getDueBorrow().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            Integer overdueFine = Math.toIntExact(diff * 50000);

            penaltyService.createPenalty(loan, overdueFine);
        }

        if (isDamagedOrLost) {
            Integer damagedOrLostFine = 5000000;
            penaltyService.createPenalty(loan, damagedOrLostFine);
        }

        BookCart bookCart = loan.getBookCarts();
        List<Book> returnedBooks = bookCart.getBook();
        updateBookStockAndRead(returnedBooks, false);

        loan.setDateReturn(currentDate);
        loanRepository.save(loan);

        return LoanResponse.builder()
                .id(loan.getId())
                .dateBorrow(loan.getDateBorrow())
                .dateReturn(currentDate)
                .dueBorrow(loan.getDueBorrow())
                .bookCartId(bookCart.getId())
                .build();
    }
    public Integer getLoanIdByAnggotaId(Integer anggotaId) {
        Optional<Loan> loan = loanRepository.findLoanAnggota(anggotaId);
        return loan.map(Loan::getId).orElse(null);
    }
}
