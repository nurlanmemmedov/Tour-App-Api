package com.example.tourappapi.exceptions;

public class LoginException extends RuntimeException{
    public LoginException(){
        super("Incorrect credentials");
    }
}
