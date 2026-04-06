package com.supermarket.pos.service;

import com.supermarket.pos.entity.User;
import com.supermarket.pos.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    public User login(String username, String password) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    public String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public String forgotPassword(String email) {

        if (email == null || email.trim().isEmpty()) {
            return "Invalid email";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Invalid email";
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "Invalid email";
        }

        String otp = generateOtp();

        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + 5 * 60 * 1000);
        userRepository.save(user);

        try {
            sendOtpEmail(email, otp);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send OTP";
        }

        return "OTP sent successfully";
    }
    public void sendOtpEmail(String email, String otp) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("POS Admin Password Reset");

        String htmlContent = "<div style=\"font-family: 'Helvetica Neue', Arial, sans-serif; max-width: 600px; margin: 0 auto; background-color: #f4f4f4; padding: 30px;\">" +
                "<div style=\"background-color: #ffffff; padding: 40px; border-radius: 16px; box-shadow: 0 8px 24px rgba(0,0,0,0.05);\">" +
                "<div style=\"text-align: center; margin-bottom: 24px;\">" +
                "<h2 style=\"color: #4F46E5; margin: 0; font-size: 28px; font-weight: 800; letter-spacing: -0.5px;\">Supermarket POS</h2>" +
                "</div>" +
                "<p style=\"font-size: 16px; color: #1E293B; margin-bottom: 16px;\">Hello,</p>" +
                "<p style=\"font-size: 16px; color: #1E293B; line-height: 1.6;\">You have requested a password reset for your POS Admin account. Use the verification code below to securely change your password.</p>" +
                "<div style=\"text-align: center; margin: 36px 0;\">" +
                "<span style=\"display: inline-block; font-size: 36px; font-weight: 900; color: #4F46E5; background-color: #EEF2FF; padding: 16px 32px; border-radius: 12px; letter-spacing: 8px; border: 2px dashed #C7D2FE;\">" + otp + "</span>" +
                "</div>" +
                "<p style=\"font-size: 14px; color: #64748B; text-align: center; margin-top: 20px;\">This code will expire in <b>5 minutes</b>.</p>" +
                "<hr style=\"border: none; border-top: 1px solid #E2E8F0; margin: 30px 0;\">" +
                "<p style=\"font-size: 12px; color: #94A3B8; text-align: center; margin: 0;\">If you didn't request this email, there's nothing to worry about - you can safely ignore it.</p>" +
                "</div>" +
                "</div>";

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    public String resetPassword(String email, String otp, String newPassword) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User not found";
        }

        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            return "Invalid OTP";
        }

        if (System.currentTimeMillis() > user.getOtpExpiry()) {
            return "OTP expired";
        }

        user.setPassword(newPassword);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return "Password updated successfully";
    }
}