package com.example.tourappapi.exceptions;

public class PasswordsAreTheSameException extends RuntimeException{
    public PasswordsAreTheSameException(){
        super("Passwords are the same");
    }
}
