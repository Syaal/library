package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class BorrowBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date peminjaman;

    @OneToOne
    private Anggota anggota;

    @ManyToOne
    private Book book;
}
