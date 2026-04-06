package com.supermarket.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MailTestController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/mail")
    public String sendTestMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("vibinav784@gmail.com"); // change this
        message.setSubject("Test Mail");
        message.setText("Hello Vibin, mail is working!");

        mailSender.send(message);

        return "Mail sent successfully!";
    }
}