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

    public String registerUser(AppUser appUserReq) {
        AppUser existingUser = appUserRepository.findAppUsersByUsername(appUserReq.getUsername());
        if (existingUser != null) {
            return "username-taken";
        }

        appUserReq.setPassword(passwordEncoder.encode(appUserReq.getPassword()));
        appUserReq.setRole("USER");

        appUserRepository.save(appUserReq);
        return "success";
    }
}
