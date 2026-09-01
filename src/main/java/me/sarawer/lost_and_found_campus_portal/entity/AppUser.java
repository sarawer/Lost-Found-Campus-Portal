package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Full name cannot be blank")
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "SEU email cannot be blank")
    @Email(message = "Please enter a valid email address")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@seu\\.edu\\.bd$",
            message = "Only Southeast University email (@seu.edu.bd) is allowed"
    )
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 4, message = "Password must contain at least 4 characters")
    private String password;

    private String role;

    @Transient
    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;


}