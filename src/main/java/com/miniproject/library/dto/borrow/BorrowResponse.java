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
    private Date borrowDate;
    //nama user dan id atau bisa langung user aja
    private String name;
    private Integer userId;
    private List<Book> bookList;

    private Date dueDate;

}
