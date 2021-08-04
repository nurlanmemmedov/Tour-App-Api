package com.example.tourappapi.exceptions;

public class NoSuchStatusException extends RuntimeException{
    public NoSuchStatusException(){
        super("No Such Status");
    }
}
