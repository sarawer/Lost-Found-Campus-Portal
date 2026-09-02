package me.sarawer.lost_and_found_campus_portal.controller;

import jakarta.validation.Valid;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        model.addAttribute("passwordMismatch", false);
        model.addAttribute("userTaken", false);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("appUser") AppUser appUser, BindingResult bindingResult, Model model) {
        model.addAttribute("passwordMismatch", false);
        model.addAttribute("userTaken", false);

        if (appUser.getConfirmPassword() == null || appUser.getConfirmPassword().isBlank()) {
            bindingResult.rejectValue("confirmPassword", "NotBlank", "Confirm password cannot be blank");
        }

        boolean passwordMismatch = appUser.getPassword() != null
                && appUser.getConfirmPassword() != null
                && !appUser.getPassword().equals(appUser.getConfirmPassword());
        model.addAttribute("passwordMismatch", passwordMismatch);

        if (bindingResult.hasErrors() || passwordMismatch) {
            return "register";
        }

        String result = appUserService.registerUser(appUser);
        boolean userTaken = result.equals("username-taken");
        model.addAttribute("userTaken", userTaken);

        if (userTaken) {
            return "register";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}