package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private LocalDate serviceDate;
    private LocalDateTime serviceStart;
    private LocalDateTime serviceEnd;
    private String notes;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    public boolean checkServiceEnd(){
        return serviceEnd.isAfter(serviceStart);
    }
}
