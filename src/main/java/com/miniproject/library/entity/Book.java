package com.miniproject.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date publicationDate;
    private Integer stock;
    private Integer read;
    @ManyToOne
    private Librarian librarian;
}
