package com.miniproject.library.repository;

import com.miniproject.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.username=:username and u.password=:password")
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
