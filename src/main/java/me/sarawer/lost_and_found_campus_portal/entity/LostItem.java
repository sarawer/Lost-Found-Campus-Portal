package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.Base64;

@Entity
@Data
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Student ID cannot be blank")
    private String studentId;

    @NotBlank(message = "Item name cannot be blank")
    private String itemName;

    private String description;
    private String lostLocation;

    @PastOrPresent(message = "Lost date cannot be in the future")
    private LocalDate lostDate;

    private String contactInfo;
    private String createdBy;
    private String status = "pending";

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    private String imageContentType;

    public String getImageDataUri() {
        if (imageData == null || imageContentType == null) {
            return null;
        }
        return "data:" + imageContentType + ";base64," + Base64.getEncoder().encodeToString(imageData);
    }
}
