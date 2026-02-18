package com.example.hotel_reservation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    @Email
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRol userRol;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomService> roomServices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public User(String userName, String userEmail, String userPassword, String name, String lastName, UserRol userRol) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.name = name;
        this.lastName = lastName;
        this.userRol = userRol;
    }

}
