package com.miniproject.library.dto.book;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class BookRequest {
    @NotBlank(message = "Masukkan Pengarang")
    private String author;
    @NotBlank(message = "Masukkan Penerbit")
    private String publisher;
    @NotBlank(message = "Masukkan Tahun Terbit")
    private Date publicationDate;
    @NotBlank(message = "Masukkan Stok")
    private Integer stock;
    @NotBlank(message = "Masukkan Judul")
    private String title;
    @NotBlank(message = "Masukkan Summary")
    private String summary;
    @NotBlank(message = "Masukkan Kategori")
    private Integer categoryId;
}
