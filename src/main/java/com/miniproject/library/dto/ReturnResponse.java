package com.miniproject.library.dto;

import com.miniproject.library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnResponse {
    private Integer id;
    private List<Book> bookList;

    private Date pengembalian;
    private BigDecimal denda;
}
