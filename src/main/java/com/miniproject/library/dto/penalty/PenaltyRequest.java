package com.miniproject.library.dto.penalty;

import lombok.Data;

@Data
public class PenaltyRequest {
    private String name;
    private String description;
    private Integer cost;
    private Integer loanId;
    private Double amount;
    private boolean damaged;
    private boolean lost;
}
