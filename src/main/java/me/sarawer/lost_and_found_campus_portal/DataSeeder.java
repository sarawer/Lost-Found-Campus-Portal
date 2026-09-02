package me.sarawer.lost_and_found_campus_portal;

import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        AppUser existingAdmin = appUserRepository.findAppUsersByUsername("admin@seu.edu.bd");

        if (existingAdmin == null) {
            AppUser admin = new AppUser();
            admin.setName("System Admin");
            admin.setUsername("admin@seu.edu.bd");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            appUserRepository.save(admin);
        }

    }
}