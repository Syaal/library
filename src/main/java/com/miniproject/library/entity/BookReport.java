package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
