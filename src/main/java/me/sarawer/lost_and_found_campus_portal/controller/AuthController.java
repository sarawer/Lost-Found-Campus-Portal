package me.sarawer.lost_and_found_campus_portal.controller;

import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute AppUser appUser, Model model) {

        // Server-side validation
        if (appUser.getName() == null || appUser.getName().isBlank()) {
            model.addAttribute("error", "Full name must not be blank.");
            model.addAttribute("appUser", appUser);
            return "register";
        }

        if (appUser.getUsername() == null || appUser.getUsername().isBlank()) {
            model.addAttribute("error", "SEU email must not be blank.");
            model.addAttribute("appUser", appUser);
            return "register";
        }

        String usernameLower = appUser.getUsername().toLowerCase();
        if (!usernameLower.endsWith("@seu.edu.bd")) {
            model.addAttribute("error", "SEU email must end with @seu.edu.bd.");
            model.addAttribute("appUser", appUser);
            return "register";
        }

        if (appUser.getPassword() == null || appUser.getPassword().isBlank()) {
            model.addAttribute("error", "Password must not be blank.");
            model.addAttribute("appUser", appUser);
            return "register";
        }

        if (appUser.getConfirmPassword() == null || appUser.getConfirmPassword().isBlank()) {
            model.addAttribute("error", "Please confirm your password.");
            model.addAttribute("appUser", appUser);
            return "register";
        }

        if (!appUser.getPassword().equals(appUser.getConfirmPassword())) {
            model.addAttribute("error", "Password and Confirm Password do not match.");
            model.addAttribute("appUser", appUser);
            return "register";
        }

        String result = appUserService.registerUser(appUser);

        if (result.equals("username-taken")) {
            model.addAttribute("error", "This username is already taken. Please choose another.");
            model.addAttribute("appUser", new AppUser());
            return "register";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}