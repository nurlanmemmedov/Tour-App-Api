package com.example.tourappapi.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super("Invalid Token");
    }
}
