package com.miniproject.library.dto.penalty;

import com.miniproject.library.entity.Loan;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PenaltyResponse {
    private Integer id;
    private Double amount;
    private Integer loanId;
}
