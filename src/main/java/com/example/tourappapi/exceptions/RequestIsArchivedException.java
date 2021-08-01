package com.example.tourappapi.exceptions;

public class RequestIsArchivedException extends RuntimeException{
    public RequestIsArchivedException(){
        super("Request is archived");
    }
}
