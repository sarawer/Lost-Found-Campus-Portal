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

        String result = appUserService.registerUser(appUser);

        if (result.equals("username-taken")) {
            model.addAttribute("error", "This username is already taken. Please choose another.");
            model.addAttribute("appUser", new AppUser());
            return "register";
        }

        return "redirect:/login?registered";
    }
}