package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.CategoryType;
import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.entity.RoomState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByRoomStatus(RoomState roomStatus);

    List<Room> findByCategoryCategoryType(CategoryType categoryType);

    Optional<Room> findByRoomNumber(String roomNumber);
}

