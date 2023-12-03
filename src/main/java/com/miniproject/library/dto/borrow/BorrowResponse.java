package com.miniproject.library.dto.borrow;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date borrowDate;
    private Integer visitorId;
    private String visitorName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;
    private List<Book> bookList;
}
