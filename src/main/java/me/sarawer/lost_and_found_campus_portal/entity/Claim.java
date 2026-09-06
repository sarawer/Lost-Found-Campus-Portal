package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name cannot be blank")
    private String itemName;

    @NotBlank(message = "Student ID cannot be blank")
    private String claimerStudentId;

    @NotBlank(message = "Full name cannot be blank")
    private String claimerName;

    private String claimerContact;

    @PastOrPresent(message = "Claim date cannot be in the future")
    private LocalDate claimDate;

    @NotBlank(message = "Status cannot be blank")
    private String status = "pending";

}