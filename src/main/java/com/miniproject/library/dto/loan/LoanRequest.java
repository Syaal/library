package com.miniproject.library.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LoanRequest {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateBorrow;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateReturn;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dueBorrow;
    private List<Integer> bookCartId;
    private Integer librarianId;
    private Integer anggotaId;
}
