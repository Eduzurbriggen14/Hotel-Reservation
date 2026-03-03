package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String description;

    private BigDecimal pricePerNight;

    private Integer maxOccupancy;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    public void addRoomToListRoom(Room room){
        if (room != null) {
            rooms.add(room);
            room.setCategory(this);
        }
    }
}
