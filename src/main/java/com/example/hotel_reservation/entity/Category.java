package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;
    private String description;
    private BigDecimal pricePerNight;
    private int maxOccupancy;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    public Category(String categoryName, String description, String categoryType, int maxOccupancy, BigDecimal pricePerNight) {
        this.categoryName = categoryName;
        this.description = description;
        this.categoryType = CategoryType.valueOf(categoryType.toUpperCase());
        this.maxOccupancy = maxOccupancy;
        this.pricePerNight = pricePerNight;
    }
}
