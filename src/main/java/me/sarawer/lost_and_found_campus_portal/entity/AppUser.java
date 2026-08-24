package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;   // "ADMIN" or "USER"
}