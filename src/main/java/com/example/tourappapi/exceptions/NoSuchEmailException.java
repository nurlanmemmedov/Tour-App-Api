package com.example.tourappapi.exceptions;

public class NoSuchEmailException extends RuntimeException{
    public NoSuchEmailException(){
        super("Incorrect email");
    }
}
