package com.miniproject.library.service;

import com.miniproject.library.dto.penalty.PenaltyRequest;
import com.miniproject.library.dto.penalty.PenaltyResponse;
import com.miniproject.library.entity.Loan;
import com.miniproject.library.entity.Penalty;
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

    public List<Penalty> getAllPenalties() {
        return penaltyRepository.findAll();
    }

    public PenaltyResponse applyPenalty(PenaltyRequest penaltyRequest, Integer loanId) {
        // Validate and process penalty logic
        if (penaltyRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Penalty amount must be greater than zero.");
        }

        // Retrieve the loan based on loanId
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with ID " + loanId + " not found."));

        // Save the penalty to the database
        Penalty penalty = new Penalty();
        penalty.setAmount(penaltyRequest.getAmount());
        penalty.setLoan(loan); // Associate the penalty with the loan
        // Set other relevant fields from the request
        // ...

        penaltyRepository.save(penalty);

        return PenaltyResponse.builder()
                .id(penalty.getId())
                .amount(penalty.getAmount())
                .loanId(loan.getId()) // Include the associated loan ID in the response
                // Include other relevant information in the response
                .build();
    }

    public PenaltyResponse getPenaltyById(Integer id) {
        Penalty penalty = penaltyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Penalty with ID " + id + " not found."));

        return PenaltyResponse.builder()
                .id(penalty.getId())
                .amount(penalty.getAmount())
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

        double overdueFine = calculateOverdueFine(loan);

        Penalty penalty = new Penalty();
        penalty.setLoan(loan);
        penalty.setAmount(overdueFine);

        return penaltyRepository.save(penalty);
    }


    // Implementasi logika perhitungan denda sesuai kebutuhan
    private double calculateOverdueFine(Loan loan) {
        // Contoh logika perhitungan denda
        // Anda dapat menyesuaikan ini sesuai dengan aturan bisnis Anda
        Date currentDate = new Date();
        Date dueDate = loan.getDueBorrow();

        if (currentDate.after(dueDate)) {
            long overdueMillis = currentDate.getTime() - dueDate.getTime();
            long overdueDays = overdueMillis / (24 * 60 * 60 * 1000);
            // Set tarif denda (anda dapat menyesuaikan ini)
            double fineRate = 0.50;
            return overdueDays * fineRate;
        }

        return 0.0; // Tidak ada denda keterlambatan
    }
}
