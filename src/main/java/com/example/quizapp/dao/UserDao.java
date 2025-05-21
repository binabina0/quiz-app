package com.example.quizapp.dao;

import com.example.quizapp.enteties.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u set u.refreshToken = :refreshToken where u.username = :username")
    @Transactional
    void updateRefreshToken(@Param("username") String username, @Param("refreshToken") String refreshToken);
}
