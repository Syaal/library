package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "BorrowBook")
@Data
public class BorrowBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date peminjaman;

    @OneToOne
    private User user;

    @ManyToOne
    private Book book;
}
