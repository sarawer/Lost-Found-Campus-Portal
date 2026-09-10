package me.sarawer.lost_and_found_campus_portal.service;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.entity.PasswordResetToken;
import me.sarawer.lost_and_found_campus_portal.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public void createPasswordResetTokenForUser(AppUser user, String appUrl) {
        String token = UUID.randomUUID().toString();
        // Token expires in 15 minutes
        PasswordResetToken myToken = new PasswordResetToken(token, user, LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(myToken);

        String resetLink = appUrl + "/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(user.getUsername(), resetLink);
    }

    public String validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passToken = tokenRepository.findByToken(token);
        
        if (passToken.isEmpty()) {
            return "invalidToken";
        }
        
        PasswordResetToken resetToken = passToken.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            return "expired";
        }
        
        return null; // Valid
    }
    
    public AppUser getUserByPasswordResetToken(String token) {
        Optional<PasswordResetToken> passToken = tokenRepository.findByToken(token);
        return passToken.map(PasswordResetToken::getAppUser).orElse(null);
    }
    
    public void deleteToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }
}
