package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String email;
    private String gender;
    @OneToMany(mappedBy = "librarian")
    private Set<Book> books;
}
