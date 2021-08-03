package com.example.tourappapi.services;

import com.example.tourappapi.services.interfaces.EmailService;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * {@inheritDoc}
     * @param email
     * @param subject
     * @param text
     * @throws MailException
     */
    @Override
    public void sendMail(String email, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress("Tour Advisor <touradvisor2021@gmail.com>"));
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(message);
        } catch (MessagingException e) {
        }
    }
}
