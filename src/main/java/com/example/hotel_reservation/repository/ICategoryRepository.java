package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryType(CategoryType categoryType);

    boolean existsByCategoryType(CategoryType categoryType);

}
