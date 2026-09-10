package me.sarawer.lost_and_found_campus_portal.controller;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.repository.AppUserRepository;
import me.sarawer.lost_and_found_campus_portal.service.AppUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository; // needed to save name changes

    @GetMapping
    public String showProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(Authentication authentication, @RequestParam String name, Model model) {
        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);
        
        if (name != null && !name.trim().isEmpty()) {
            user.setName(name.trim());
            appUserRepository.save(user);
            return "redirect:/profile?success";
        }
        
        return "redirect:/profile?error=invalid_name";
    }

    @PostMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {
        
        String username = authentication.getName();
        AppUser user = appUserService.findByUsername(username);

        // Verify current password
        if (!appUserService.verifyPassword(user, currentPassword)) {
            return "redirect:/profile?error=wrong_password";
        }

        // Verify new passwords match
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/profile?error=password_mismatch";
        }
        
        // Verify minimum length (optional additional check here)
        if (newPassword.length() < 4) {
            return "redirect:/profile?error=password_short";
        }

        appUserService.updatePassword(user, newPassword);
        return "redirect:/profile?pwd_success";
    }
}
