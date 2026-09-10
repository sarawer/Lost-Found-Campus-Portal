package me.sarawer.lost_and_found_campus_portal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtp(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("SEU CampusFinder - Email Verification");

        message.setText(
                "Hello,\n\n" +
                        "Your SEU CampusFinder verification code is:\n\n" +
                        otp +
                        "\n\n" +
                        "This code will expire in 5 minutes.\n\n" +
                        "If you did not try to register, you can ignore this email.\n\n" +
                        "Regards,\n" +
                        "SEU CampusFinder"
        );

        log.info("Attempting to send OTP email to: {}", to);
        mailSender.send(message);
        log.info("Successfully sent OTP email to: {}", to);
    }

    public void sendPasswordResetEmail(String to, String resetLink) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("SEU CampusFinder - Password Reset Request");

        message.setText(
                "Hello,\n\n" +
                        "We received a request to reset your SEU CampusFinder password.\n\n" +
                        "Click the link below to reset it:\n\n" +
                        resetLink +
                        "\n\n" +
                        "This link will expire in 15 minutes.\n\n" +
                        "If you did not request this, you can ignore this email.\n\n" +
                        "Regards,\n" +
                        "SEU CampusFinder"
        );

        log.info("Attempting to send password reset email to: {}", to);
        mailSender.send(message);
        log.info("Successfully sent password reset email to: {}", to);
    }
}