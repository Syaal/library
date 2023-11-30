package com.miniproject.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    @NotBlank(message = "Masukkan Judul")
    private String judulBuku;
    @NotBlank(message = "Masukkan Penerbit")
    private String penerbit;
    @NotBlank(message = "Masukkan Pengarang")
    private String pengarang;
    @NotBlank(message = "Masukkan Tahun")
    private Integer tahunTerbit;
    @NotBlank(message = "Masukkan Stock")
    private Integer stock;
}
