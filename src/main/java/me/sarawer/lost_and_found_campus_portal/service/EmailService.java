package me.sarawer.lost_and_found_campus_portal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

        System.out.println("BEFORE EMAIL");
        mailSender.send(message);
        System.out.println("AFTER EMAIL");
    }
}