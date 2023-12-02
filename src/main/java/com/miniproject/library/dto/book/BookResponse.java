package com.miniproject.library.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date publicationDate;
    private Integer stock;
    private Integer read;
}
