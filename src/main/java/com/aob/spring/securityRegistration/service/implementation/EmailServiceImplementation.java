package com.aob.spring.securityRegistration.service.implementation;

import com.aob.spring.securityRegistration.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplementation implements EmailService {

    private JavaMailSender javaMailSender;

    public EmailServiceImplementation(@Autowired JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String emailAddress, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("art_of_buiding@gmail.com");
        simpleMailMessage.setTo(emailAddress);
        simpleMailMessage.setSubject("Confirmation email");
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
