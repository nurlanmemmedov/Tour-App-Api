package com.example.tourappapi.exceptions;

public class NotWorkTimeException extends RuntimeException{
    public NotWorkTimeException(){
        super("Not working hours");
    }
}
