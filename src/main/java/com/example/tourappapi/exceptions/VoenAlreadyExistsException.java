package com.example.tourappapi.exceptions;

public class VoenAlreadyExistsException extends RuntimeException{
    public VoenAlreadyExistsException(){
        super("Voen already exists");
    }
}
