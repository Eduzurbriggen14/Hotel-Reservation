package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser_UserId(Long userId);

    List<Reservation> findByRoom_RoomNumber(String roomNumber);





}
