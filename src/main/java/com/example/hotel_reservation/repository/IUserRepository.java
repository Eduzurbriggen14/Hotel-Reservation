package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.entity.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    // custom query methods
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserEmailIgnoreCase(String userEmail);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserName(String userName);
    List<User> findByUserRol(UserRol userRol);
}
