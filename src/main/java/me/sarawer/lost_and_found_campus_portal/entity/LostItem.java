package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    private String itemName;
    private String description;
    private String lostLocation;
    private LocalDate lostDate;
    private String contactInfo;
    private String createdBy;
    private String status = "pending";
}
