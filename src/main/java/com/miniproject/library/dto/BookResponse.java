package com.miniproject.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private Integer id;
    private String judulBuku;
    private String penerbit;
    private String pengarang;
    private Integer tahunTerbit;
    private Integer stock;
    private Integer reading;
    private Integer waiting;
}
