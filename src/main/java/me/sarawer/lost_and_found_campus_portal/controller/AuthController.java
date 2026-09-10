package me.sarawer.lost_and_found_campus_portal.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sarawer.lost_and_found_campus_portal.entity.AppUser;
import me.sarawer.lost_and_found_campus_portal.service.AppUserService;
import me.sarawer.lost_and_found_campus_portal.service.EmailService;
import me.sarawer.lost_and_found_campus_portal.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;


    // =========================
    // REGISTER PAGE
    // =========================

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("appUser", new AppUser());
        model.addAttribute("passwordMismatch", false);
        model.addAttribute("userTaken", false);

        return "register";
    }


    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("appUser") AppUser appUser,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        model.addAttribute("passwordMismatch", false);
        model.addAttribute("userTaken", false);


        // Confirm password validation
        if (appUser.getConfirmPassword() == null
                || appUser.getConfirmPassword().isBlank()) {

            bindingResult.rejectValue(
                    "confirmPassword",
                    "NotBlank",
                    "Confirm password cannot be blank"
            );
        }


        // Password mismatch
        boolean passwordMismatch =
                appUser.getPassword() != null
                        && appUser.getConfirmPassword() != null
                        && !appUser.getPassword()
                        .equals(appUser.getConfirmPassword());

        model.addAttribute("passwordMismatch", passwordMismatch);


        // Validation errors
        if (bindingResult.hasErrors() || passwordMismatch) {
            return "register";
        }


        // Check email already exists
        String result =
                appUserService.checkUsername(appUser.getUsername());

        boolean userTaken = result.equals("username-taken");

        model.addAttribute("userTaken", userTaken);

        if (userTaken) {
            return "register";
        }


        // Generate 6 digit OTP
        String otp = String.format(
                "%06d",
                new Random().nextInt(1000000)
        );


        // Store registration data in session
        session.setAttribute("pendingUser", appUser);

        session.setAttribute("otp", otp);

        session.setAttribute(
                "otpExpiry",
                System.currentTimeMillis() + (5 * 60 * 1000)
        );


        // Send OTP email
        try {

            emailService.sendOtp(
                    appUser.getUsername(),
                    otp
            );

        } catch (Exception e) {
            log.error("Failed to send OTP email to user {}. Exception type: {}, Message: {}", 
                    appUser.getUsername(), e.getClass().getName(), e.getMessage(), e);

            model.addAttribute(
                    "emailError",
                    "Unable to send verification email. Please try again."
            );

            return "register";
        }


        return "redirect:/verify-otp";
    }


    // =========================
    // OTP PAGE
    // =========================

    @GetMapping("/verify-otp")
    public String showVerifyOtpPage(HttpSession session, Model model) {

        AppUser pendingUser =
                (AppUser) session.getAttribute("pendingUser");

        if (pendingUser == null) {
            return "redirect:/register";
        }

        model.addAttribute(
                "email",
                pendingUser.getUsername()
        );

        return "verify-otp";
    }


    // =========================
    // VERIFY OTP
    // =========================

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam("otp") String enteredOtp,
            HttpSession session,
            Model model) {


        AppUser pendingUser =
                (AppUser) session.getAttribute("pendingUser");

        String storedOtp =
                (String) session.getAttribute("otp");

        Long expiry =
                (Long) session.getAttribute("otpExpiry");


        // No registration session
        if (pendingUser == null
                || storedOtp == null
                || expiry == null) {

            return "redirect:/register";
        }


        // OTP expired
        if (System.currentTimeMillis() > expiry) {

            model.addAttribute(
                    "error",
                    "OTP has expired. Please register again."
            );

            model.addAttribute(
                    "email",
                    pendingUser.getUsername()
            );

            return "verify-otp";
        }


        // Wrong OTP
        if (!storedOtp.equals(enteredOtp)) {

            model.addAttribute(
                    "error",
                    "Invalid verification code."
            );

            model.addAttribute(
                    "email",
                    pendingUser.getUsername()
            );

            return "verify-otp";
        }


        // OTP correct
        appUserService.saveVerifiedUser(pendingUser);


        // Clear session
        session.removeAttribute("pendingUser");
        session.removeAttribute("otp");
        session.removeAttribute("otpExpiry");


        return "redirect:/login?registered";
    }


    // =========================
    // LOGIN PAGE
    // =========================

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // =========================
    // FORGOT PASSWORD
    // =========================

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(
            jakarta.servlet.http.HttpServletRequest request,
            @RequestParam("email") String userEmail,
            Model model) {

        AppUser user = appUserService.findByUsername(userEmail);
        
        model.addAttribute("message", "If an account exists for this email, a password reset link has been sent.");
        
        if (user != null) {
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            passwordResetService.createPasswordResetTokenForUser(user, appUrl);
        }

        return "forgot-password";
    }

    // =========================
    // RESET PASSWORD
    // =========================

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        String result = passwordResetService.validatePasswordResetToken(token);
        
        if (result != null) {
            return "redirect:/login?resetError";
        }
        
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        String result = passwordResetService.validatePasswordResetToken(token);
        if (result != null) {
            return "redirect:/login?resetError";
        }
        
        if (password == null || password.length() < 4) {
            model.addAttribute("error", "Password must contain at least 4 characters.");
            model.addAttribute("token", token);
            return "reset-password";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("token", token);
            return "reset-password";
        }

        AppUser user = passwordResetService.getUserByPasswordResetToken(token);
        if (user != null) {
            appUserService.updatePassword(user, password);
            passwordResetService.deleteToken(token);
            return "redirect:/login?resetSuccess";
        } else {
            return "redirect:/login?resetError";
        }
    }
}