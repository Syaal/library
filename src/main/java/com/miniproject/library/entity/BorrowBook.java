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
    private Date borrowDate;
    private Date dueDate;
    @ManyToOne
    private Visitor visitor;
}
