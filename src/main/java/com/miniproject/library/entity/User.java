package com.miniproject.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "Users")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    @Column(length = 500)
    private String token;
    @OneToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    @OneToOne
    @JoinColumn(name = "librarian_id")
    private Librarian librarian;
}
