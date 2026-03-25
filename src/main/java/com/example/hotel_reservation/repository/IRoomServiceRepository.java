package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.entity.RoomService;
import com.example.hotel_reservation.entity.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoomServiceRepository extends JpaRepository<RoomService, Long> {

    List<RoomService> findByServiceStatus(ServiceStatus serviceStatus);
}
