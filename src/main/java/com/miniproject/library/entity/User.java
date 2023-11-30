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
    private String nama;
    private String alamat;
    private String email;
    @JsonIgnore
    private String password;
    private String role;
    private String token;
}
