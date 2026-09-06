package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class FoundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Student ID cannot be blank")
    private String studentId;

    @NotBlank(message = "Item name cannot be blank")
    private String itemName;

    private String description;
    private String foundLocation;

    @PastOrPresent(message = "Found date cannot be in the future")
    private LocalDate foundDate;

    private String contactInfo;
    private String createdBy;
    private String status = "pending";

    private LocalDateTime createdAt = LocalDateTime.now();

    private String imageUrl;

}
