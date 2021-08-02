package com.example.tourappapi.services.interfaces;

/**
 * represents the EmailService throughout the application
 */
public interface EmailService {

    /**
     * sends new email
     * @param email
     * @param subject
     * @param text
     */
    void sendMail(String email, String subject, String text);
}
