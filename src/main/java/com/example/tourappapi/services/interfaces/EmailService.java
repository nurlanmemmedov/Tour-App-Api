package com.example.tourappapi.services.interfaces;

public interface EmailService {
    void sendMail(String email, String subject, String text);
}
