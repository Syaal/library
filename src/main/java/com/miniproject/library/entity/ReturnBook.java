package com.miniproject.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Table(name = "ReturnBook")
@Entity
@Data
public class ReturnBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date pengembalian;
    private BigDecimal denda;
}
