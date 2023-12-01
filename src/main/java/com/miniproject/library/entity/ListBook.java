package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ListBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private BorrowBook borrowBook;
    @ManyToOne
    private Book book;
}
