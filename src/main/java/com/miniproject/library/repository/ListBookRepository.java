package com.miniproject.library.repository;

import com.miniproject.library.entity.ListBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListBookRepository extends JpaRepository<ListBook, Integer> {
}
