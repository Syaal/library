package com.miniproject.library.controller;

import com.miniproject.library.dto.penalty.PenaltyRequest;
import com.miniproject.library.dto.penalty.PenaltyResponse;
import com.miniproject.library.entity.Penalty;
import com.miniproject.library.service.PenaltyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/penalty")
public class PenaltyController {
    private final PenaltyService penaltyService;

    @GetMapping("/all")
    public ResponseEntity<List<Penalty>> getAllPenalties() {
        List<Penalty> penalties = penaltyService.getAllPenalties();
        return ResponseEntity.ok(penalties);
    }
    @PostMapping("/apply/{loanId}")
    public ResponseEntity<PenaltyResponse> applyPenalty(@RequestBody PenaltyRequest penaltyRequest,
                                                        @PathVariable Integer loanId) {
        try {
            PenaltyResponse penaltyResponse = penaltyService.applyPenalty(penaltyRequest, loanId);
            return ResponseEntity.ok(penaltyResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<PenaltyResponse> getPenaltyById(@PathVariable Integer id) {
        try {
            PenaltyResponse penaltyResponse = penaltyService.getPenaltyById(id);
            return ResponseEntity.ok(penaltyResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{penaltyId}")
    public ResponseEntity<Void> deletePenalty(@PathVariable Integer id) {
        try {
            penaltyService.deletePenalty(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/check/{loanId}")
    public ResponseEntity<Boolean> checkPenalty(@PathVariable Integer id) {
        try {
            Penalty penalty = penaltyService.calculatePenalty(id);
            return ResponseEntity.ok(penalty.getAmount() > 0);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
