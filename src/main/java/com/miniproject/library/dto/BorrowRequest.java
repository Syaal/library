package com.miniproject.library.dto;

import com.miniproject.library.entity.Book;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Masukkan Id Anggota")
    private Integer userId;
    @NotBlank(message = "Buku tidak boleh kosong")
    private List<Integer> bookList;
}
