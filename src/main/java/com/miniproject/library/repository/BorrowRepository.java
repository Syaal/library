package com.miniproject.library.repository;

import com.miniproject.library.entity.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BorrowRepository extends JpaRepository<BorrowBook,Integer> {
}
