package com.miniproject.library.repository;

import com.miniproject.library.entity.ReturnBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReturnRepository extends JpaRepository<ReturnBook,Integer> {
}
