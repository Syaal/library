package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private Integer publicationDate;
    private Integer stock;
    private Integer read;
    @ManyToOne
    private Librarian librarian;
}
