package com.miniproject.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String judulBuku;
    private String penerbit;
    private String pengarang;
    private Integer tahunTerbit;
    private Integer stock;
    private Integer reading;
    private Integer waiting;
}
