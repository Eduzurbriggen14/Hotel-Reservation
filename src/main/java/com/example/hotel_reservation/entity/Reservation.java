package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private BigDecimal totalAmount;

    private int numberOfGuests;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @PrePersist
    @PreUpdate
    public void calculateAmount() {
        this.totalAmount = calculateTotalAmount();
    }

    public boolean checkCheckOut(){
        return checkOutDate.isAfter(checkInDate);
    }

    public BigDecimal calculateTotalAmount(){
        if (room == null || room.getCategory() == null) return BigDecimal.ZERO;

        long days = checkOutDate.toEpochDay() - checkInDate.toEpochDay();
        if (days <= 0) return BigDecimal.ZERO;

        if (numberOfGuests > room.getCategory().getMaxOccupancy()){
            BigDecimal total;
            BigDecimal extraPrice;
            switch (room.getCategory().getCategoryType().name()){
                case "SIMPLE":
                    extraPrice = BigDecimal.valueOf((numberOfGuests - room.getCategory().getMaxOccupancy()) * 500 * days);
                    total = room.getCategory().getPricePerNight().multiply(BigDecimal.valueOf(days)).add(extraPrice);
                    return total;
                case "DOUBLE":
                    extraPrice = BigDecimal.valueOf((numberOfGuests - room.getCategory().getMaxOccupancy()) * 700 * days);
                    total = room.getCategory().getPricePerNight().multiply(BigDecimal.valueOf(days)).add(extraPrice);
                    return total;
                case "SUITE":
                    extraPrice = BigDecimal.valueOf((numberOfGuests - room.getCategory().getMaxOccupancy()) * 1000 * days);
                    total = room.getCategory().getPricePerNight().multiply(BigDecimal.valueOf(days)).add(extraPrice);
                    return total;
                default:
                    break;
            }
        }

        return room.getCategory().getPricePerNight().multiply(BigDecimal.valueOf(days));
    }
}
