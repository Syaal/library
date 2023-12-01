package com.miniproject.library.dto.ListBook;

import com.miniproject.library.entity.Book;
import com.miniproject.library.entity.BorrowBook;
import lombok.Data;

@Data
public class ListBookRequest {
    private BorrowBook borrowBook;
    private Book book;
    private String status;
}
