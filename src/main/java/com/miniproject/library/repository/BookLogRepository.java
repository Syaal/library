package com.miniproject.library.repository;

import com.miniproject.library.entity.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLogRepository extends JpaRepository<BookLog, Integer> {
}
