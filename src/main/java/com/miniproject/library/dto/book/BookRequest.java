package com.miniproject.library.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    @NotBlank(message = "Masukkan Judul")
    private String title;
    @NotBlank(message = "Masukkan Pengarang")
    private String author;
    @NotBlank(message = "Masukkan Penerbit")
    private String publisher;
    @NotNull(message = "Masukkan Tahun")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date publicationDate;
    @NotNull(message = "Masukkan Stock")
    private Integer stock;
}
