package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer penalty;
    @OneToOne
    private ReturnBook returnBook;
}
