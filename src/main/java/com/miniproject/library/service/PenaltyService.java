package com.miniproject.library.service;

import com.miniproject.library.dto.penalty.PenaltyRequest;
import com.miniproject.library.dto.penalty.PenaltyResponse;
import com.miniproject.library.entity.Book;
import com.miniproject.library.entity.Loan;
import com.miniproject.library.entity.Penalty;
import com.miniproject.library.repository.BookRepository;
import com.miniproject.library.repository.LoanRepository;
import com.miniproject.library.repository.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanService loanService;

    public List<Penalty> getAllPenalties() {
        return penaltyRepository.findAll();
    }

    public PenaltyResponse processPenaltyForLateReturnAndDamage(PenaltyRequest penaltyRequest, Integer loanId, boolean isDamaged, boolean isLost) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with ID " + loanId + " not found"));

        if (loan.getDateReturn() == null) {
            // Hanya berikan denda jika buku belum dikembalikan
            Integer overdueFine = calculateOverdueFine(loan);

            if (overdueFine > 0) {
                Penalty penalty = new Penalty();
                penalty.setLoan(loan);
                penalty.setCost(overdueFine);
                penaltyRepository.save(penalty);

                return PenaltyResponse.builder()
                        .id(penalty.getId())
                        .loanId(loan.getId())
                        .cost(penalty.getCost())
                        .build();
            }
        }
        if (isDamaged || isLost) {
            // Handle logic for damaged or lost books
            List<Book> returnedBooks = loan.getBookCarts().getBook();

            if (isDamaged) {
                replaceDamagedBooks(returnedBooks);
            }

            if (isLost) {
                replaceLostBooks(returnedBooks);
            }
            loanService.updateStockAndRead(returnedBooks, false);
        }
        return null;
    }

    private Integer calculateOverdueFine(Loan loan) {
        Date currentDate = new Date();
        Date dueDate = loan.getDueBorrow();
        Date returnDate = loan.getDateReturn();

        int maxOverdueMillis = 7 * 24 * 60 * 60 * 1000; // Misalnya, batas 7 hari

        if (returnDate.after(dueDate) && currentDate.after(dueDate) && currentDate.before(returnDate)) {
            long overdueMillis = Math.min(returnDate.getTime(), currentDate.getTime()) - dueDate.getTime();
            long overdueDays = overdueMillis / (24 * 60 * 60 * 1000);
            Integer fineRate = 1;
            return Math.toIntExact(Math.min(overdueDays, maxOverdueMillis / (24 * 60 * 60 * 1000)) * fineRate);
        }

        return 0;
    }

    private void replaceDamagedBooks(List<Book> returnedBooks) {
        for (Book damagedBook : returnedBooks) {
            damagedBook.setActive(false);
            Book newCopy = createNewCopy(damagedBook);
            bookRepository.save(newCopy);
        }
    }

    private void replaceLostBooks(List<Book> returnedBooks) {
        for (Book lostBook : returnedBooks) {
            lostBook.setActive(false);
            Book newCopy = createNewCopy(lostBook);
            bookRepository.save(newCopy);
        }
    }

    private Book createNewCopy(Book originalBook) {
        Book newCopy = new Book();
        newCopy.setAuthor(originalBook.getAuthor());
        newCopy.setTitle(originalBook.getTitle());
        newCopy.setPublisher(originalBook.getPublisher());
        newCopy.setSummary(originalBook.getSummary());
        newCopy.setPublicationDate(originalBook.getPublicationDate());
        newCopy.setStock(1);

        return newCopy;
    }

    public PenaltyResponse getPenaltyById(Integer id) {
        Penalty penalty = penaltyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Penalty with ID " + id + " not found."));

        return PenaltyResponse.builder()
                .id(penalty.getId())
                .cost(penalty.getCost())
                .loanId(penalty.getLoan().getId()) // Include the associated loan ID in the response
                // Include other relevant information in the response
                .build();
    }

    public void deletePenalty(Integer id) {
        if (penaltyRepository.existsById(id)) {
            penaltyRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Penalty with ID " + id + " Not Found");
        }
    }


    public Penalty calculatePenalty(Integer id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with ID " + id + " Not Found"));

        Integer overdueFine = calculateOverdueFine(loan);

        Penalty penalty = new Penalty();
        penalty.setLoan(loan);
        penalty.setCost(overdueFine);

        return penaltyRepository.save(penalty);
    }
}

