package com.example.tourappapi.exceptions;

public class OldPassIsIncorrectException extends RuntimeException{
    public OldPassIsIncorrectException(){
        super("Incorrect password");
    }
}
