package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name of the user
    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;   // "ADMIN" or "USER"

    // Transient field for confirm password during registration only; not persisted
    @Transient
    private String confirmPassword;
}