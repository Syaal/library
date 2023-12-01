package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long nik;
    private String name;
    private String address;
    private String email;
    private String password;
    private String gender;
    @OneToMany(mappedBy = "visitor")
    private Set<BorrowBook> borrowBook;
}
