package com.miniproject.library.dto.borrow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miniproject.library.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BorrowRequest {
    @NotNull(message = "Masukkan Id Anggota")
    private Integer visitorId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date borrowDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;
    @NotBlank(message = "Buku tidak boleh kosong")
    private List<Integer> bookList;
}
