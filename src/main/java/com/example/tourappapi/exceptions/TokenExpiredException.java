package com.example.tourappapi.exceptions;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(){
        super("Expired token");
    }
}
