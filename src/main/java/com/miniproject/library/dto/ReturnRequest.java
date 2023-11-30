package com.miniproject.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnRequest {
    @NotBlank(message = "Masukkan Nama")
    private String nama;
    private List<Integer> bookList;
}
