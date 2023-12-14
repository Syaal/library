package com.miniproject.library.dto.book;

import lombok.Data;

import java.util.Date;

@Data
public class BookRequest {
    private String author;
    private String publisher;
    private Date publicationDate;
    private Integer stock;
    private String title;
    private String summary;
    private Integer categoryId;
}
