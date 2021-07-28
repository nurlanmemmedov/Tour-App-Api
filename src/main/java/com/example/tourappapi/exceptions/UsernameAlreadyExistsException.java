package com.example.tourappapi.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(){
        super("Username already exists");
    }
}
