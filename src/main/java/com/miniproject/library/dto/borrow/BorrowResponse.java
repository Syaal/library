package com.miniproject.library.dto.borrow;

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
    //nama user dan id atau bisa langung user aja
    private String nama;
    private Integer userId;
    private List<Book> bookList;

    private Date pengembalian;

}
