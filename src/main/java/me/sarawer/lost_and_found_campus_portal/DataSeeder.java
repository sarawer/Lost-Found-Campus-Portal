package me.sarawer.lost_and_found_campus_portal;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        AppUser existingAdmin = appUserRepository.findAppUsersByUsername("admin@seu.edu.bd");

        if (existingAdmin == null) {
            AppUser admin = new AppUser();
            admin.setName("System Admin");
            admin.setUsername("admin@seu.edu.bd");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setEnabled(true);
            appUserRepository.save(admin);
        } else if (!existingAdmin.isEnabled()) {
            existingAdmin.setEnabled(true);
            appUserRepository.save(existingAdmin);
        }

    }
}