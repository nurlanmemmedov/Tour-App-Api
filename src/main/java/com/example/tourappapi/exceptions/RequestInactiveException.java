package com.example.tourappapi.exceptions;

public class RequestInactiveException extends RuntimeException{
    public RequestInactiveException(){
        super("Inactive Request");
    }
}
