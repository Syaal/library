package com.miniproject.library.dto;

import com.miniproject.library.entity.Book;
import com.miniproject.library.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowResponse {
    private Integer id;
    private Date peminjaman;
    private String nama;
    private List<Book> bookList;

    private Date pengembalian;

}
