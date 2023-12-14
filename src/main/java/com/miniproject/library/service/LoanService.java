package com.miniproject.library.service;

import com.miniproject.library.dto.bookcart.BookCartRequest;
import com.miniproject.library.dto.loan.LoanResponse;
import com.miniproject.library.entity.*;
import com.miniproject.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;

    private final AnggotaRepository anggotaRepository;

    private final LibrarianRepository librarianRepository;

    private final BookCartRepository bookCartRepository;

    private final BookRepository bookRepository;

    private final PenaltyRepository penaltyRepository;

    public LoanResponse borrowBooks(BookCartRequest bookCartRequest){

        Anggota anggota = anggotaRepository.findById(bookCartRequest.getAnggotaId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Anggota It's Not Exist!!!"));
        List<Book> books = bookRepository.findAllById(bookCartRequest.getBookIds());
        List<Book> availableBooks = getAvailableBook(books);

        if (availableBooks.isEmpty()){
            increaseWishList(books);
            throw new IllegalArgumentException("Books Out of Stock");
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
        loan.setBookCarts(bookCartRepository.findById(bookCart.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Id BookCart It's Not Exist!!!")));
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

    public LoanResponse returnBook(Integer id,Integer librarianId) {
        // Memeriksa Izin Mengembalikan Buku Pada Pustakawan
        Librarian librarian = librarianRepository.findById(librarianId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Librarian with id " + librarianId + " not found"));

        // Mengembalikan buku by id Loan
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Not Found with ID " + id));

        // Memeriksa apakah pinjaman sudah dikembalikan
        if (loan.getDateReturn() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loan with ID " + id + " has already been returned");
        }

        // Tetapkan tanggal pengembalian
        loan.setDateReturn(new Date());
        loanRepository.save(loan);

        // Ambil BookCart yang terkait dengan Pinjaman
        BookCart bookCart = loan.getBookCarts();

        // Retrieve books in the BookCart
        List<Book> returnedBooks = bookCart.getBook();

        return LoanResponse.builder()
                .id(loan.getId())
                .bookCartId(bookCart.getId())
                .dateBorrow(loan.getDateBorrow())
                .dueBorrow(loan.getDueBorrow())
                .dateReturn(loan.getDateReturn())
                .build();
    }

    public void updateStockAndRead(List<Book> books, boolean increaseRead) {
        for (Book book : books) {
            if (increaseRead) {
                // tambahkan stock
                book.setStock(book.getStock() + 1);
                // Kurangi jumlah baca
                Integer currentRead = book.getRead();
                book.setRead(currentRead != null ? Math.max(currentRead - 1, 0) : 0);
            } else {
                // Kurangi Stock
                Integer currentStock = book.getStock();
                book.setStock(currentStock != null ? Math.max(currentStock - 1, 0) : 0);
            }
            bookRepository.save(book);
        }
    }
}
