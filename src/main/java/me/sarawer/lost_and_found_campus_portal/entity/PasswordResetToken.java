package me.sarawer.lost_and_found_campus_portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;

    @Column(nullable = false)
    private LocalDateTime expiryDate;
    
    public PasswordResetToken(String token, AppUser appUser, LocalDateTime expiryDate) {
        this.token = token;
        this.appUser = appUser;
        this.expiryDate = expiryDate;
    }
}
