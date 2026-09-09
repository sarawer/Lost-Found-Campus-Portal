package me.sarawer.lost_and_found_campus_portal.service;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public String checkUsername(String username) {

        AppUser existingUser =
                appUserRepository.findAppUsersByUsername(username);

        if (existingUser != null) {
            return "username-taken";
        }

        return "available";
    }

    public void saveVerifiedUser(AppUser appUser) {

        appUser.setPassword(
                passwordEncoder.encode(appUser.getPassword())
        );

        appUser.setRole("USER");
        appUser.setEnabled(true);

        appUserRepository.save(appUser);
    }
}